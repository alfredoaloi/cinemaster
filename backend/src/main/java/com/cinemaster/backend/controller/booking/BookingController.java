package com.cinemaster.backend.controller.booking;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.*;
import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.service.*;
import com.cinemaster.backend.email.EmailService;
import com.cinemaster.backend.pdf.PdfService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/booking")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class BookingController {

    public static final Long EXPIRATION_MINUTES = 10L;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private EventService eventService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfService pdfService;

    @PostMapping("/coupon")
    public ResponseEntity validateCoupon(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody String code) {
        System.out.println(code);
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            Optional<CouponDto> optional = couponService.findByCode(code);
            if (!(optional.isPresent())) {
                throw new CouponNotFoundException();
            }
            CouponDto coupon = optional.get();
            if (!(couponService.findAllByUserId(accountDto.getId()).contains(coupon))) {
                throw new CouponNotFoundException();
            } else {
                coupon.setUsed(true);
                couponService.update(coupon);
                return ResponseEntity.ok(optional.get().getValue());
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/select")
    public ResponseEntity selectBooking(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody List<BookingCreationParams> bookingCreationParamsList) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && (accountDto instanceof UserPasswordLessDto || accountDto instanceof CashierPasswordLessDto)) {
            List<BookingDto> bookings = new ArrayList<>();
            try {
                for (BookingCreationParams bookingCreationParams : bookingCreationParamsList) {
                    for (SeatDto seat : bookingCreationParams.getSeats()) {
                        BookingDto bookingDto = new BookingDto();
                        bookingDto.setEvent(bookingCreationParams.getEvent());
                        bookingDto.setSeat(seat);
                        if (accountDto instanceof UserPasswordLessDto) {
                            bookingDto.setUser(modelMapper.map(accountDto, UserPasswordLessDto.class));
                            bookingDto.setCashier(null);
                        } else if (accountDto instanceof CashierPasswordLessDto) {
                            bookingDto.setCashier(modelMapper.map(accountDto, CashierPasswordLessDto.class));
                            bookingDto.setUser(null);
                        }
                        bookingDto.setPayed(false);
                        bookingDto.setExpiration(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
                        bookingDto.setPrice(0D);
                        bookingService.save(bookingDto);
                        bookings.add(bookingDto);
                    }
                }
            } catch (EventNotFoundException | RoomNotFoundException | SeatNotFoundException | InvalidDataException | BookingAlreadyPresentException e) {
                for (BookingDto bookingDto : bookings) {
                    bookingService.delete(bookingDto);
                }
                throw e;
            }
            return ResponseEntity.ok(bookings);
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/confirm")
    public ResponseEntity confirmBooking(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody BookingConfirmationParams[] bookings) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && (accountDto instanceof UserPasswordLessDto || accountDto instanceof CashierPasswordLessDto)) {
            for (BookingConfirmationParams param : bookings) {
                BookingDto booking = bookingService.findById(param.getBooking().getId()).orElseThrow(() -> new BookingNotFoundException());
                booking.setPayed(true);
                booking.setPrice(param.getPrice());
                booking.setExpiration(null);
                bookingService.update(booking);

                EventDto event = eventService.findById(booking.getEvent().getId()).orElseThrow(() -> new EventNotFoundException());
                SeatDto seat = seatService.findById(booking.getSeat().getId()).orElseThrow(() -> new SeatNotFoundException());

                TicketDto ticketDto = new TicketDto();
                ticketDto.setBookingId(booking.getId());
                if (accountDto instanceof UserPasswordLessDto) {
                    ticketDto.setUserName(((UserPasswordLessDto) accountDto).getFirstName() + " " + ((UserPasswordLessDto) accountDto).getLastName());
                } else if (accountDto instanceof CashierPasswordLessDto) {
                    ticketDto.setUserName("");
                }
                ticketDto.setShowName(event.getShow().getName());
                ticketDto.setRoomName(event.getRoom().getName());
                ticketDto.setSeat(seat.getRow() + seat.getColumn() + " - " + seat.getSeatType());
                ticketDto.setDate(event.getDate());
                ticketDto.setStartTime(event.getStartTime());
                ticketDto.setPrice(booking.getPrice());
                String barcode;
                do {
                    barcode = ticketDto.generateBarcodeEAN13();
                } while (ticketService.findByBarcode(barcode).isPresent());
                ticketDto.setBarcode(barcode);
                ticketService.save(ticketDto);

                String ticketPath = pdfService.createPdf(ticketDto);
                if (accountDto instanceof UserPasswordLessDto) {
                    emailService.sendTicketEmail(((UserPasswordLessDto) accountDto).getEmail(), ticketDto, ticketPath);
                    couponService.deleteAllByUserIdAndIsUsed(accountDto.getId());
                } else if (accountDto instanceof CashierPasswordLessDto) {
                    // TODO STAMPO IL BIGLIETTO
                }
            }
            return ResponseEntity.ok("Booking confirmed successfully");
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/remove")
    public ResponseEntity removeBooking(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody BookingDto[] bookings) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && (accountDto instanceof UserPasswordLessDto || accountDto instanceof CashierPasswordLessDto)) {
            for (BookingDto dto : bookings) {
                BookingDto booking = bookingService.findById(dto.getId()).orElseThrow(() -> new BookingNotFoundException());

                // se provo a disdire online ma non trovo la prenotazione
                if (accountDto instanceof UserPasswordLessDto && !(booking.getUser().getId().equals(accountDto.getId()))) {
                    throw new BookingNotFoundException();
                }
                bookingService.delete(booking);
                TicketDto ticket = ticketService.findByBookingId(dto.getId()).orElseThrow(() -> new BookingNotFoundException());
                ticketService.delete(ticket);

                if (accountDto instanceof CashierPasswordLessDto) {
                    System.out.println("Tieni i soldi");
                    // TODO restituisco i soldi al cliente a mano
                } else if (accountDto instanceof UserPasswordLessDto) {
                    CouponDto coupon = new CouponDto();
                    coupon.setUser((UserPasswordLessDto) accountDto);
                    coupon.setUsed(false);
                    coupon.setValue(booking.getPrice());
                    coupon.setCode(couponService.generateCouponCode());
                    couponService.save(coupon);
                    // TODO inviare un messaggio più bello, con i dettagli del coupon
                    emailService.sendCouponEmail(((UserPasswordLessDto) accountDto).getEmail(), booking.getId(), coupon);
                }
            }
            return ResponseEntity.ok("Booking deleted successfully");
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/ticket")
    public ResponseEntity pdf(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestParam(name = "id", required = true) Long bookingId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto instanceof CashierPasswordLessDto) {
            String path = "project" + File.separator + "tickets" + File.separator + bookingId + ".pdf";
            File file = new File(path);
            byte[] contents = new byte[0];
            try {
                contents = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new BookingNotFoundException();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData(String.format("Ticket-%d.pdf", bookingId), String.format("Ticket-%d.pdf", bookingId));
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
            return response;
        } else {
            throw new ForbiddenException();
        }
    }
}
