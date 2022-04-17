package com.cinemaster.backend.service;

import com.cinemaster.backend.TestUtils;
import com.cinemaster.backend.data.dto.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingServiceTest extends ServiceTest {

    @Test
    public void testFindByIdAndFindAll() {
        ShowDto showDto = TestUtils.createShowDto("Film 1");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("Sala 1", 2, 3);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        UserDto userDto = TestUtils.createUserDto("ciccio");
        accountService.save(userDto);

        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        BookingDto bookingDto = TestUtils.createBookingDto(userPasswordLessDto, roomDto.getSeats().get(0), eventDto);
        bookingService.save(bookingDto);

        Optional<BookingDto> optional = bookingService.findById(bookingDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        bookingDto.setId(null);
        bookingDto = TestUtils.createBookingDto(userPasswordLessDto, roomDto.getSeats().get(1), eventDto);
        bookingService.save(bookingDto);

        List<BookingDto> bookings = bookingService.findAll();
        Assert.assertEquals(2, bookings.size());

        for (BookingDto booking : bookings) {
            bookingService.delete(booking);
        }
    }

    @Test
    public void testDeleteExpired() {
        ShowDto showDto = TestUtils.createShowDto("Film 2");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("Sala 2", 2, 3);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        UserDto userDto = TestUtils.createUserDto("alfral");
        accountService.save(userDto);

        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        BookingDto bookingDto = TestUtils.createBookingDto(userPasswordLessDto, roomDto.getSeats().get(0), eventDto);
        bookingService.save(bookingDto);

        List<BookingDto> bookings = bookingService.findAll();
        Assert.assertEquals(1, bookings.size());
        bookingService.deleteExpired();
        bookings = bookingService.findAll();
        Assert.assertEquals(0, bookings.size());
    }

    @Test
    public void testFindBookedSeatsByEventId() {
        ShowDto showDto = TestUtils.createShowDto("Film 3");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("Sala 3", 2, 3);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        UserDto userDto = TestUtils.createUserDto("ewfgrethy");
        accountService.save(userDto);

        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        BookingDto bookingDto = TestUtils.createBookingDto(userPasswordLessDto, roomDto.getSeats().get(0), eventDto);
        bookingService.save(bookingDto);

        List<SeatDto> seats = bookingService.findBookedSeatsByEventId(eventDto.getId());
        Assert.assertEquals(1, seats.size());
    }

    @Test
    public void testFindAllByCashier() {
        ShowDto showDto = TestUtils.createShowDto("Film 4");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("Sala 4", 2, 3);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        CashierDto cashierDto = TestUtils.createCashierDto("sdfgf");
        accountService.save(cashierDto);

        CashierPasswordLessDto cashierPasswordLessDto = modelMapper.map(cashierDto, CashierPasswordLessDto.class);

        BookingDto bookingDto = TestUtils.createBookingDto(cashierPasswordLessDto, roomDto.getSeats().get(0), eventDto);
        bookingService.save(bookingDto);

        List<BookingDto> bookings = bookingService.findAllByCashier();
        Assert.assertEquals(1, bookings.size());
    }
}
