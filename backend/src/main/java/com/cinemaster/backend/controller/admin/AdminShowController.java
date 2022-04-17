package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.BookingsPresentException;
import com.cinemaster.backend.core.exception.EventNotFoundException;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.ShowDto;
import com.cinemaster.backend.data.service.EventService;
import com.cinemaster.backend.data.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/shows")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminShowController {

    @Autowired
    private ShowService showService;

    @Autowired
    private EventService eventService;

    @GetMapping("")
    public ResponseEntity showList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(showService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity showAdd(@RequestBody ShowDto showDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            showService.save(showDto);
            return ResponseEntity.ok(showDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity showEdit(@RequestBody ShowDto showDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            showService.update(showDto);
            return ResponseEntity.ok(showDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity showDelete(@RequestBody ShowDto showDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            List<EventDto> deleted = new ArrayList<>();
            try {
                List<EventDto> events = eventService.findAllByShowId(showDto.getId());
                for (EventDto event : events) {
                    eventService.delete(event);
                    deleted.add(event);
                }
                showService.delete(showDto);
                return ResponseEntity.ok("Successfully deleted");
            } catch (EventNotFoundException | BookingsPresentException e) {
                for (EventDto event : deleted) {
                    eventService.save(event);
                }
                throw e;
            }
        } else {
            throw new ForbiddenException();
        }
    }

}
