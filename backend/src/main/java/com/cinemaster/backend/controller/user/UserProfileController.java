package com.cinemaster.backend.controller.user;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.*;
import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.service.*;
import com.cinemaster.backend.email.EmailService;
import com.cinemaster.backend.pdf.PdfService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserProfileController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private PdfService pdfService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/profile")
    public ResponseEntity profile(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            return ResponseEntity.ok(accountDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/tickets")
    public ResponseEntity bookings(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            return ResponseEntity.ok(ticketService.findAllByUserId(accountDto.getId()));
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/update")
    public ResponseEntity update(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody UserPasswordLessDto userDto) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            accountService.update(userDto);
            UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);
            CookieMap.getInstance().getMap().remove(sessionId);
            CookieMap.getInstance().getMap().put(sessionId, userPasswordLessDto);
            return ResponseEntity.ok(userPasswordLessDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity changePassword(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody UserDto userDto) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            String hashedPassword = DigestUtils.sha256Hex(userDto.getHashedPassword());
            UserPasswordLessDto userPasswordLessDto = (UserPasswordLessDto) accountService.changePassword(userDto.getId(), hashedPassword);
            CookieMap.getInstance().getMap().remove(sessionId);
            CookieMap.getInstance().getMap().put(sessionId, userPasswordLessDto);
            return ResponseEntity.ok(userPasswordLessDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("/tickets/re-send")
    public ResponseEntity reSendTicket(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId,
            @RequestBody BookingDto dto) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof UserPasswordLessDto) {
            BookingDto booking = bookingService.findById(dto.getId()).orElseThrow(() -> new BookingNotFoundException());
            if (!(booking.getPayed())) {
                throw new BookingNotPayedException();
            }

            EventDto event = eventService.findById(booking.getEvent().getId()).orElseThrow(() -> new EventNotFoundException());
            SeatDto seat = seatService.findById(booking.getSeat().getId()).orElseThrow(() -> new SeatNotFoundException());

            TicketDto ticketDto = new TicketDto();
            ticketDto.setBookingId(booking.getId());
            ticketDto.setUserName(((UserPasswordLessDto) accountDto).getFirstName() + " " + ((UserPasswordLessDto) accountDto).getLastName());
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
            ticketService.update(ticketDto);

            String ticketPath = pdfService.createPdf(ticketDto);
            // TODO inviare un messaggio più bello, con i dettagli dell'ordine
            emailService.sendTicketEmail(((UserPasswordLessDto) accountDto).getEmail(), ticketDto, ticketPath);
            return ResponseEntity.ok("Ticket sent by email");
        } else {
            throw new ForbiddenException();
        }
    }

}
