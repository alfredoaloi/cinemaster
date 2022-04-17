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
public class TicketServiceTest extends ServiceTest {

    @Test
    public void testTicketService() {
        ShowDto showDto = TestUtils.createShowDto("wertyrutyi");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("waesgrdhtyjkgu", 2, 3);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        UserDto userDto = TestUtils.createUserDto("mascalzone");
        accountService.save(userDto);

        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        BookingDto bookingDto = TestUtils.createBookingDto(userPasswordLessDto, roomDto.getSeats().get(0), eventDto);
        bookingService.save(bookingDto);

        TicketDto ticketDto = TestUtils.createTicketDto(bookingDto.getId(), "ABC");
        ticketService.save(ticketDto);

        Optional<TicketDto> optional = ticketService.findByBarcode("ABC");
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        optional = ticketService.findByBookingId(bookingDto.getId());
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }

        List<TicketDto> tickets = ticketService.findAllByUserId(userDto.getId());
        Assert.assertEquals(1, tickets.size());
    }
}
