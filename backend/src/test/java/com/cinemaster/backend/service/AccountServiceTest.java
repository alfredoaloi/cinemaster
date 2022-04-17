package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.entity.User;
import com.cinemaster.backend.data.service.AccountService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountServiceTest extends ServiceTest {

    @Test
    public void testSaveAndCheckCredentials() {
        AdminDto adminDto = new AdminDto();
        adminDto.setUsername("admin");
        adminDto.setHashedPassword("admin");
        accountService.save(adminDto);

        Optional<AccountPasswordLessDto> optional = accountService.checkCredentials("admin", "admin");
        if (optional.isPresent()) {
            Assert.assertTrue(optional.get() instanceof AdminPasswordLessDto);
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testSaveAndUpdateAndChangePasswordAndCheckCredentials() {
        UserDto userDto = new UserDto();
        userDto.setUsername("alfredo");
        userDto.setHashedPassword("alfredo");
        userDto.setBirthdate(LocalDate.now());
        userDto.setEmail("email@ibero.it");
        userDto.setGender(User.Gender.MALE);
        userDto.setFirstName("Alfredo");
        userDto.setLastName("Cognome");
        accountService.save(userDto);

        Optional<AccountPasswordLessDto> optional = accountService.checkCredentials("alfredo", "alfredo");
        if (optional.isPresent()) {
            Assert.assertTrue(optional.get() instanceof UserPasswordLessDto);
        } else {
            Assert.fail();
        }

        UserPasswordLessDto userPasswordLessDto = (UserPasswordLessDto) accountService.checkCredentials("alfredo", "alfredo").get();
        Assert.assertEquals(userDto.getEmail(), userPasswordLessDto.getEmail());
        userPasswordLessDto.setEmail("ciao.ciao@ciao.it");
        accountService.update(userPasswordLessDto);
        userPasswordLessDto = (UserPasswordLessDto) accountService.checkCredentials("alfredo", "alfredo").get();
        Assert.assertEquals("ciao.ciao@ciao.it", userPasswordLessDto.getEmail());

        userPasswordLessDto = (UserPasswordLessDto) accountService.checkCredentials("alfredo", "alfredo").get();
        accountService.changePassword(userPasswordLessDto.getId(), "password");
        optional = accountService.checkCredentials("alfredo", "password");
        if (optional.isPresent()) {
            Assert.assertTrue(optional.get() instanceof UserPasswordLessDto);
        } else {
            Assert.fail();
        }
    }
}
