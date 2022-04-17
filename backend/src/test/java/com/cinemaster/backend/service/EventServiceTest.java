package com.cinemaster.backend.service;

import com.cinemaster.backend.TestUtils;
import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.RoomDto;
import com.cinemaster.backend.data.dto.ShowDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EventServiceTest extends ServiceTest {

    @Test
    public void testEventService() {
        for (BookingDto bookingDto : bookingService.findAll()) {
            bookingService.delete(bookingDto);
        }
        for (EventDto eventDto : eventService.findAll()) {
            eventService.delete(eventDto);
        }

        ShowDto showDto = TestUtils.createShowDto("rfoeier");
        showService.save(showDto);

        RoomDto roomDto = TestUtils.createRoomDto("fgh", 2, 5);
        roomService.save(roomDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventService.save(eventDto);

        List<EventDto> events = eventService.findAllByShowId(showDto.getId());
        Assert.assertEquals(1, events.size());

        eventDto.setDate(LocalDate.now());
        eventService.update(eventDto);

        events = eventService.findAllByToday();
        Assert.assertEquals(1, events.size());

        eventDto.setDate(LocalDate.now().plusDays(10));
        eventService.update(eventDto);

        events = eventService.findAllByToday();
        Assert.assertEquals(0, events.size());

        eventService.deleteOld();
        events = eventService.findAll();
        Assert.assertEquals(1, events.size());
    }
}
