package com.cinemaster.backend.data.dto;

import com.cinemaster.backend.data.entity.Seat;

public class SeatDto {

    private Long id;

    private String row;

    private String column;

    private Seat.Type seatType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Seat.Type getSeatType() {
        return seatType;
    }

    public void setSeatType(Seat.Type seatType) {
        this.seatType = seatType;
    }
}
