package com.cinemaster.backend;

import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.entity.Price;
import com.cinemaster.backend.data.entity.Seat;
import com.cinemaster.backend.data.entity.Show;
import com.cinemaster.backend.data.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class TestUtils {

    public static ShowDto createShowDto(String name) {
        ShowDto showDto = new ShowDto();
        showDto.setComingSoon(true);
        showDto.setDescription("Descrizione");
        showDto.setHighlighted(true);
        showDto.setLanguage("Italiano");
        showDto.setLength(100L);
        showDto.setName(name);
        showDto.setProductionLocation("Italia");
        showDto.setPhotoUrl("https://www.google.it");
        showDto.setUrlHighlighted("https://www.google.it");
        showDto.setUrlTrailer("https://www.google.it");
        showDto.setRating(Show.Rating.PT);
        showDto.setReleaseDate(LocalDate.now());
        showDto.setActors(new ArrayList<>());
        showDto.setCategories(new ArrayList<>());
        showDto.setDirectors(new ArrayList<>());
        return showDto;
    }

    public static List<SeatDto> createSeats(int rows, int columns) {
        List<SeatDto> seats = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                SeatDto seatDto = new SeatDto();
                seatDto.setRow("R" + i);
                seatDto.setColumn("C" + j);
                seatDto.setSeatType(Seat.Type.VIP);
                seats.add(seatDto);
            }
        }
        return seats;
    }

    public static RoomDto createRoomDto(String name, int rows, int columns) {
        RoomDto roomDto = new RoomDto();
        roomDto.setName(name);
        roomDto.setnRows(Integer.toUnsignedLong(rows));
        roomDto.setnColumns(Integer.toUnsignedLong(columns));
        roomDto.setSeats(createSeats(rows, columns));
        return roomDto;
    }

    public static EventDto createEventDto(RoomDto roomDto, ShowDto showDto) {
        EventDto eventDto = new EventDto();
        eventDto.setRoom(roomDto);
        eventDto.setShow(showDto);
        Price price = new Price();
        price.setStandardPrice(10.0);
        price.setPremiumPrice(15.0);
        price.setVipPrice(20.0);
        eventDto.setPrice(price);
        eventDto.setDate(LocalDate.now().plusDays(1));
        eventDto.setStartTime(LocalTime.now());
        eventDto.setEndTime(LocalTime.now().plusMinutes(showDto.getLength()));
        return eventDto;
    }

    public static BookingDto createBookingDto(AccountPasswordLessDto accountPasswordLessDto, SeatDto seatDto, EventDto eventDto) {
        BookingDto bookingDto = new BookingDto();
        if (accountPasswordLessDto instanceof UserPasswordLessDto) {
            bookingDto.setUser((UserPasswordLessDto) accountPasswordLessDto);
        } else if (accountPasswordLessDto instanceof CashierPasswordLessDto) {
            bookingDto.setCashier((CashierPasswordLessDto) accountPasswordLessDto);
        }
        bookingDto.setPayed(true);
        bookingDto.setSeat(seatDto);
        bookingDto.setEvent(eventDto);
        bookingDto.setExpiration(LocalDateTime.now());
        return bookingDto;
    }

    public static UserDto createUserDto(String username) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setHashedPassword(username);
        userDto.setEmail(username + "@libero.it");
        userDto.setBirthdate(LocalDate.now());
        userDto.setFirstName(username);
        userDto.setLastName(username);
        userDto.setGender(User.Gender.MALE);
        return userDto;
    }

    public static CashierDto createCashierDto(String username) {
        CashierDto cashierDto = new CashierDto();
        cashierDto.setUsername(username);
        cashierDto.setHashedPassword(username);
        return cashierDto;
    }

    public static CouponDto createCouponDto(UserPasswordLessDto userPasswordLessDto, String code) {
        CouponDto couponDto = new CouponDto();
        couponDto.setUser(userPasswordLessDto);
        couponDto.setCode(code);
        couponDto.setValue(12.0);
        couponDto.setUsed(false);
        return couponDto;
    }

    public static TicketDto createTicketDto(Long bookingId, String barcode) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setBarcode(barcode);
        ticketDto.setBookingId(bookingId);
        return ticketDto;
    }
}
