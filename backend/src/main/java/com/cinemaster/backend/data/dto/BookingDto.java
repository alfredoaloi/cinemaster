package com.cinemaster.backend.data.dto;

import java.time.LocalDateTime;

public class BookingDto {

    private Long id;

    private EventDto event;

    private SeatDto seat;

    private UserPasswordLessDto user;

    private CashierPasswordLessDto cashier;

    private Boolean payed;

    private LocalDateTime expiration;

    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    public SeatDto getSeat() {
        return seat;
    }

    public void setSeat(SeatDto seat) {
        this.seat = seat;
    }

    public UserPasswordLessDto getUser() {
        return user;
    }

    public void setUser(UserPasswordLessDto user) {
        this.user = user;
    }

    public Boolean getPayed() {
        return payed;
    }

    public void setPayed(Boolean payed) {
        this.payed = payed;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public CashierPasswordLessDto getCashier() {
        return cashier;
    }

    public void setCashier(CashierPasswordLessDto cashier) {
        this.cashier = cashier;
    }
}
