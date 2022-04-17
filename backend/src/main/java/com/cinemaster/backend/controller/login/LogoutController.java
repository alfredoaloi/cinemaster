package com.cinemaster.backend.controller.login;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/logout")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class LogoutController {

    @PostMapping("")
    public ResponseEntity logout(@CookieValue(value = "sessionid", defaultValue = "") String sessionId, HttpServletResponse httpServletResponse) {
        CookieMap.getInstance().getMap().remove(sessionId);
        ResponseCookie cookie = ResponseCookie.from("sessionid", null)
                .sameSite("Strict")
                .httpOnly(true)
                .path("/")
                .maxAge(0)
                .build();
        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("successfully logged out");
    }

}
