package com.cinemaster.backend.controller.cashier;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.CashierPasswordLessDto;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.service.BookingService;
import com.cinemaster.backend.data.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cashier")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class CashierController {

    @Autowired
    private EventService eventService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/today-events")
    public ResponseEntity eventOfToday(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof CashierPasswordLessDto) {
            List<EventDto> events = eventService.findAllByToday();
            return ResponseEntity.ok(events);
        } else {
            throw new ForbiddenException();
        }
    }

    @GetMapping("/bookings")
    public ResponseEntity bookings(
            @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof CashierPasswordLessDto) {
            List<BookingDto> bookings = bookingService.findAllByCashier();
            return ResponseEntity.ok(bookings);
        } else {
            throw new ForbiddenException();
        }
    }
}
