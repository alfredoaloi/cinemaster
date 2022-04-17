package com.cinemaster.backend.controller.booking;

import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.dto.SeatDto;

import java.util.List;

public class BookingCreationParams {

    private EventDto event;

    private List<SeatDto> seats;

    public EventDto getEvent() {
        return event;
    }

    public void setEvent(EventDto event) {
        this.event = event;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }
}
