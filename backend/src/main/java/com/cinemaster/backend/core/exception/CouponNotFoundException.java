package com.cinemaster.backend.core.exception;

public class CouponNotFoundException extends RuntimeException {

    public CouponNotFoundException() {
        super("Coupon not found");
    }

}
