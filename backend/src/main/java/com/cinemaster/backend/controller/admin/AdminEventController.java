package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.EventsNotCreatedException;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.core.exception.RoomNotFoundException;
import com.cinemaster.backend.core.exception.ShowNotFoundException;
import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.service.BookingService;
import com.cinemaster.backend.data.service.EventService;
import com.cinemaster.backend.data.service.RoomService;
import com.cinemaster.backend.data.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/admin/events")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminEventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private ShowService showService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public ResponseEntity eventList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(eventService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity eventAdd(@RequestBody EventCreationParams eventCreationParams, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            LocalDate index = LocalDate.of(eventCreationParams.getStartDate().getYear(), eventCreationParams.getStartDate().getMonth(), eventCreationParams.getStartDate().getDayOfMonth());
            List<EventDto> droppedEvents = new ArrayList<>();
            while (index.isBefore(eventCreationParams.getEndDate().plusDays(1))) {
                for (LocalTime startTime : eventCreationParams.getStartTimes()) {
                    EventDto eventDto = new EventDto();
                    ShowDto show = showService.findById(eventCreationParams.getShow().getId()).orElseThrow(() -> new ShowNotFoundException());
                    RoomDto room = roomService.findById(eventCreationParams.getRoom().getId()).orElseThrow(() -> new RoomNotFoundException());
                    show.setComingSoon(false);
                    showService.update(show);
                    eventDto.setShow(show);
                    eventDto.setRoom(room);
                    eventDto.setPrice(eventCreationParams.getPrice());
                    eventDto.setDate(index);
                    eventDto.setStartTime(startTime);
                    LocalTime endTime = startTime.plusMinutes(show.getLength());
                    eventDto.setEndTime(endTime);
                    Optional<EventDto> optional = eventService.save(eventDto);
                    if (optional.isPresent()) {
                        droppedEvents.add(optional.get());
                    }
                }
                index = index.plusDays(1);
            }
            if (droppedEvents.isEmpty()) {
                return ResponseEntity.ok("Events created successfully");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                for (EventDto event : droppedEvents) {
                    stringBuilder.append(event.getDate() + " - " + event.getStartTime() + " - " + event.getEndTime() + "\n");
                }
                throw new EventsNotCreatedException(stringBuilder.toString());
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity eventEdit(@RequestBody EventDto eventDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            eventService.update(eventDto);
            return ResponseEntity.ok(eventDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity eventDelete(@RequestBody EventDto eventDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            eventService.delete(eventDto);
            return ResponseEntity.ok("Successfully deleted");
        } else {
            throw new ForbiddenException();
        }
    }
}
