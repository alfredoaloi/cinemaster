package com.cinemaster.backend.service;

import com.cinemaster.backend.TestUtils;
import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.RoomDto;
import com.cinemaster.backend.data.dto.ShowDto;
import com.cinemaster.backend.data.specification.ShowSpecification;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ShowServiceTest extends ServiceTest {

    @Test
    public void testFindByFilter() {
        CategoryDto action = new CategoryDto();
        action.setName("Action");
        categoryService.save(action);

        CategoryDto adventure = new CategoryDto();
        adventure.setName("Adventure");
        categoryService.save(adventure);

        ShowDto show1 = TestUtils.createShowDto("Show1");
        show1.getCategories().add(action);
        showService.save(show1);

        ShowDto show2 = TestUtils.createShowDto("Show1");
        show2.getCategories().add(adventure);
        showService.save(show2);

        List<ShowDto> shows = showService.findAllByFilter(new ShowSpecification.Filter(null, null, null, "Action"));
        Assert.assertEquals(1, shows.size());

        shows = showService.findAllByFilter(new ShowSpecification.Filter(null, null, null, "Adventure"));
        Assert.assertEquals(1, shows.size());

        shows = showService.findAllByFilter(new ShowSpecification.Filter("Show", null, null));
        Assert.assertEquals(2, shows.size());
    }

    @Test
    public void testFindAllByEventBeforeNextWeek() {
        for (EventDto eventDto : eventService.findAll()) {
            eventService.delete(eventDto);
        }

        RoomDto roomDto = TestUtils.createRoomDto("xgfcghvjb", 1, 1);
        roomService.save(roomDto);

        ShowDto showDto = TestUtils.createShowDto("sdgfhjk");
        showService.save(showDto);

        EventDto eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventDto.setDate(LocalDate.now().plusDays(1));
        eventService.save(eventDto);

        showDto = TestUtils.createShowDto("rtyuy");
        showService.save(showDto);

        eventDto = TestUtils.createEventDto(roomDto, showDto);
        eventDto.setDate(LocalDate.now().plusDays(10));
        eventService.save(eventDto);

        List<ShowDto> shows = showService.findAllByEventBeforeNextWeek();
        Assert.assertEquals(1, shows.size());
    }
}
