package com.cinemaster.backend.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private Double value;

    @Column(name = "used")
    private Boolean used;

    @ManyToOne
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
