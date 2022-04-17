package com.cinemaster.backend.data.dto;

import java.util.Objects;

public class CouponDto {

    private Long id;

    private String code;

    private Double value;

    private Boolean used;

    private UserPasswordLessDto user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    public UserPasswordLessDto getUser() {
        return user;
    }

    public void setUser(UserPasswordLessDto user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponDto couponDto = (CouponDto) o;
        return id.equals(couponDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
