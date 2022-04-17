package com.cinemaster.backend.controller.booking;

import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.SeatDto;

import java.util.List;

public class BookingConfirmationParams {

    private BookingDto booking;

    private Double price;

    public BookingDto getBooking() {
        return booking;
    }

    public void setBooking(BookingDto booking) {
        this.booking = booking;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
