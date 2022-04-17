package com.cinemaster.backend.service;

import com.cinemaster.backend.TestUtils;
import com.cinemaster.backend.data.dto.CouponDto;
import com.cinemaster.backend.data.dto.UserDto;
import com.cinemaster.backend.data.dto.UserPasswordLessDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CouponServiceTest extends ServiceTest {

    @Test
    public void testCouponService() {
        UserDto userDto = TestUtils.createUserDto("efrgthy");
        accountService.save(userDto);
        UserPasswordLessDto userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        CouponDto couponDto = TestUtils.createCouponDto(userPasswordLessDto, "xtfchgjvhkb");
        couponService.save(couponDto);

        userDto = TestUtils.createUserDto("23243");
        accountService.save(userDto);
        userPasswordLessDto = modelMapper.map(userDto, UserPasswordLessDto.class);

        couponDto = TestUtils.createCouponDto(userPasswordLessDto, "3425346");
        couponService.save(couponDto);

        List<CouponDto> coupons = couponService.findAll();
        Assert.assertEquals(2, coupons.size());

        coupons = couponService.findAllByUserId(userDto.getId());
        Assert.assertEquals(1, coupons.size());

        couponDto.setUsed(true);
        couponService.save(couponDto);

        couponService.deleteAllByUserIdAndIsUsed(userDto.getId());
        coupons = couponService.findAll();
        Assert.assertEquals(1, coupons.size());

        Optional<CouponDto> optional = couponService.findByCode("xtfchgjvhkb");
        if (optional.isPresent()) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }
}
