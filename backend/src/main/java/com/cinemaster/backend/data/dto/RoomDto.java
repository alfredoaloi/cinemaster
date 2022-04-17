package com.cinemaster.backend.data.dto;

import java.util.List;

public class RoomDto {

    private Long id;

    private String name;

    private Long nRows;

    private Long nColumns;

    private List<SeatDto> seats;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getnRows() {
        return nRows;
    }

    public void setnRows(Long nRows) {
        this.nRows = nRows;
    }

    public Long getnColumns() {
        return nColumns;
    }

    public void setnColumns(Long nColumns) {
        this.nColumns = nColumns;
    }

    public List<SeatDto> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDto> seats) {
        this.seats = seats;
    }
}
