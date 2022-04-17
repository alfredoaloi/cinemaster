package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.UserNotFoundException;
import com.cinemaster.backend.data.dao.CouponDao;
import com.cinemaster.backend.data.dao.UserDao;
import com.cinemaster.backend.data.dto.CouponDto;
import com.cinemaster.backend.data.entity.Coupon;
import com.cinemaster.backend.data.entity.User;
import com.cinemaster.backend.data.service.CouponService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponDao couponDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponDao.saveAndFlush(coupon);
        couponDto.setId(coupon.getId());
    }

    @Override
    public void update(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponDao.saveAndFlush(coupon);
    }


    @Override
    public void delete(CouponDto couponDto) {
        Coupon coupon = modelMapper.map(couponDto, Coupon.class);
        couponDao.delete(coupon);
    }

    @Override
    public Optional<CouponDto> findById(Long id) {
        Optional<Coupon> optional = couponDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(coupon -> modelMapper.map(coupon, CouponDto.class));
        }
        return Optional.empty();
    }

    @Override
    public Optional<CouponDto> findByCode(String code) {
        Optional<Coupon> optional = couponDao.findByCode(code);
        if (optional.isPresent()) {
            return optional.map(coupon -> modelMapper.map(coupon, CouponDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<CouponDto> findAll() {
        return couponDao.findAll().stream().map(coupon -> modelMapper.map(coupon, CouponDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<CouponDto> findAllByUserId(Long id) {
        Optional<User> optional = userDao.findById(id);
        if (optional.isPresent()) {
            return couponDao.findAllByUserId(id).stream().map(coupon -> modelMapper.map(coupon, CouponDto.class)).collect(Collectors.toList());
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    @Transactional
    public void deleteAllByUserIdAndIsUsed(Long id) {
        couponDao.deleteAllByUserIdAndUsed(id, true);
    }

    @Override
    @Transactional
    public String generateCouponCode() {
        int length = 6;
        StringBuilder builder = new StringBuilder();
        do {
            String bigString = DigestUtils.sha256Hex(Integer.toString(new Random().nextInt()));
            for (int i = 0; i < length; i++) {
                char character = bigString.charAt(new Random().nextInt(bigString.length()));
                builder.append(character);
            }
        } while (couponDao.findByCode(builder.toString().toUpperCase()).isPresent());
        return builder.toString().toUpperCase();
    }
}
