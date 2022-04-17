package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CouponDao extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    Optional<Coupon> findByCode(String code);

    List<Coupon> findAllByUserId(Long id);

    void deleteAllByUserIdAndUsed(Long id, Boolean used);
}
