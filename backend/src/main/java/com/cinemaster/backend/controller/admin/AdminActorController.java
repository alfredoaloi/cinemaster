package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.ActorDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/actors")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminActorController {

    @Autowired
    private ActorService actorService;

    @GetMapping("")
    public ResponseEntity actorList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(actorService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity actorAdd(@RequestBody ActorDto actorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            actorService.save(actorDto);
            return ResponseEntity.ok(actorDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity actorEdit(@RequestBody ActorDto actorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            actorService.update(actorDto);
            return ResponseEntity.ok(actorDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity actorDelete(@RequestBody ActorDto actorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            actorService.delete(actorDto);
            return ResponseEntity.ok("Successfully deleted");
        } else {
            throw new ForbiddenException();
        }
    }

}
