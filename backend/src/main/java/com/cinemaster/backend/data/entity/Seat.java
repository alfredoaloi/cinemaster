package com.cinemaster.backend.data.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "seat_row")
    private String row;

    @Column(name = "seat_column")
    private String column;

    @Column(name = "seat_type")
    @Enumerated(EnumType.STRING)
    private Type seatType;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "seat")
    private List<Booking> bookings;

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

    public Type getSeatType() {
        return seatType;
    }

    public void setSeatType(Type seatType) {
        this.seatType = seatType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public enum Type {STANDARD, PREMIUM, VIP}
}
