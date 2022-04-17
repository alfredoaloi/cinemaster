package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.dto.DirectorDto;
import com.cinemaster.backend.data.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/directors")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminDirectorController {

    @Autowired
    private DirectorService directorService;

    @GetMapping("")
    public ResponseEntity directorList(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok(directorService.findAll());
        } else {
            throw new ForbiddenException();
        }
    }

    @PostMapping("")
    public ResponseEntity directorAdd(@RequestBody DirectorDto directorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            directorService.save(directorDto);
            return ResponseEntity.ok(directorDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @PutMapping("")
    public ResponseEntity directorEdit(@RequestBody DirectorDto directorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            directorService.update(directorDto);
            return ResponseEntity.ok(directorDto);
        } else {
            throw new ForbiddenException();
        }
    }

    @DeleteMapping("")
    public ResponseEntity directorDelete(@RequestBody DirectorDto directorDto, @CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            directorService.delete(directorDto);
            return ResponseEntity.ok("Successfully deleted");
        } else {
            throw new ForbiddenException();
        }
    }
}
