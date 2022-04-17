package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.CouponDto;

import java.util.List;
import java.util.Optional;

public interface CouponService {

    void save(CouponDto couponDto);

    void update(CouponDto couponDto);

    void delete(CouponDto couponDto);

    Optional<CouponDto> findById(Long id);

    Optional<CouponDto> findByCode(String code);

    List<CouponDto> findAll();

    List<CouponDto> findAllByUserId(Long id);

    void deleteAllByUserIdAndIsUsed(Long id);

    String generateCouponCode();
}
