package com.cinemaster.backend.controller.login;

import com.cinemaster.backend.core.exception.WrongCredentialsException;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;
import com.cinemaster.backend.data.dto.AdminPasswordLessDto;
import com.cinemaster.backend.data.dto.CashierPasswordLessDto;
import com.cinemaster.backend.data.dto.UserPasswordLessDto;
import com.cinemaster.backend.data.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping(path = "/login")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @PostMapping("")
    public ResponseEntity login(@RequestBody Credentials credentials, @CookieValue(value = "sessionid", defaultValue = "") String sessionId, HttpServletResponse httpServletResponse) {
        AccountPasswordLessDto accountDto = CookieMap.getInstance().getMap().get(sessionId);
        if (accountDto != null) {
            if (accountDto instanceof AdminPasswordLessDto) {
                AdminPasswordLessDto adminDto = (AdminPasswordLessDto) accountDto;
                return ResponseEntity.ok(adminDto);
            } else if (accountDto instanceof UserPasswordLessDto) {
                UserPasswordLessDto userDto = (UserPasswordLessDto) accountDto;
                return ResponseEntity.ok(userDto);
            } else if (accountDto instanceof CashierPasswordLessDto) {
                CashierPasswordLessDto cashierDto = (CashierPasswordLessDto) accountDto;
                return ResponseEntity.ok(cashierDto);
            }
        }
        Optional<AccountPasswordLessDto> optional = accountService.checkCredentials(credentials.getUsername(), DigestUtils.sha256Hex(credentials.getPassword()));
        if (optional.isPresent()) {
            String cookieString = DigestUtils.sha256Hex(Integer.toString(new Random().nextInt()));
            ResponseCookie cookie = ResponseCookie.from("sessionid", cookieString)
                    .sameSite("Strict")
                    .httpOnly(true)
                    .path("/")
                    .build();
            httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            AccountPasswordLessDto dto = optional.get();
            if (dto instanceof AdminPasswordLessDto) {
                AdminPasswordLessDto adminDto = (AdminPasswordLessDto) dto;
                CookieMap.getInstance().getMap().put(cookieString, adminDto);
                return ResponseEntity.ok(adminDto);
            } else if (dto instanceof UserPasswordLessDto) {
                UserPasswordLessDto userDto = (UserPasswordLessDto) dto;
                CookieMap.getInstance().getMap().put(cookieString, userDto);
                return ResponseEntity.ok(userDto);
            } else if (dto instanceof CashierPasswordLessDto) {
                CashierPasswordLessDto cashierDto = (CashierPasswordLessDto) dto;
                CookieMap.getInstance().getMap().put(cookieString, cashierDto);
                return ResponseEntity.ok(cashierDto);
            }
        } else {
            throw new WrongCredentialsException();
        }
        return null;
    }

}
