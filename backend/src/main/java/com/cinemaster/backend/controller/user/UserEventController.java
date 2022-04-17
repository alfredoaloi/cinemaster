package com.cinemaster.backend.controller.user;

import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.SeatDto;
import com.cinemaster.backend.data.service.BookingService;
import com.cinemaster.backend.data.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/shows/events")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserEventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public ResponseEntity eventOfShowList(
            @RequestParam(value = "id", required = true) Long id) {
        List<EventDto> events = eventService.findAllByShowIdAfterNow(id);
        return ResponseEntity.ok(events);
    }

    @GetMapping("/seats/booked")
    public ResponseEntity bookedSeatsOfEventList(
            @RequestParam(value = "id", required = true) Long id) {
        List<SeatDto> seats = bookingService.findBookedSeatsByEventId(id);
        return ResponseEntity.ok(seats);
    }

}
