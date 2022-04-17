package com.cinemaster.backend.controller.user;

import com.cinemaster.backend.data.dto.UserDto;
import com.cinemaster.backend.data.dto.UserPasswordLessDto;
import com.cinemaster.backend.data.service.AccountService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/registration")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserRegistrationController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity registration(@RequestBody UserDto userDto) {
        userDto.setHashedPassword(DigestUtils.sha256Hex(userDto.getHashedPassword()));
        accountService.save(userDto);
        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        return ResponseEntity.ok(userPasswordLessDto);
    }

}
