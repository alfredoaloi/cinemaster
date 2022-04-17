package com.cinemaster.backend.controller.admin;

import com.cinemaster.backend.controller.login.CookieMap;
import com.cinemaster.backend.core.exception.ForbiddenException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/dashboard")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class AdminDashboardController {

    @GetMapping("")
    public ResponseEntity adminDashboard(@CookieValue(value = "sessionid", defaultValue = "") String sessionId) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null && accountDto instanceof AdminPasswordLessDto) {
            return ResponseEntity.ok("Authorized");
        } else {
            throw new ForbiddenException();
        }
    }

}
