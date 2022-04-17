package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.dto.RoomDto;
import com.cinemaster.backend.data.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/rooms")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminRoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping("")
    public ResponseEntity roomList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(roomService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity roomAdd(@RequestBody RoomDto roomDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            roomService.save(roomDto);
            return ResponseEntity.ok(roomDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity roomEdit(@RequestBody RoomDto roomDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            roomService.update(roomDto);
            return ResponseEntity.ok(roomDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity roomDelete(@RequestBody RoomDto roomDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            roomService.delete(roomDto);
            return ResponseEntity.ok(roomDto);
        } else {
            throw new ForbiddenException();
        }
    }
}
