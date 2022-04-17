package com.cinemaster.backend.data.entity;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Size(min = 1, max = 20, message = "The name length must be between 1 and 20")
    @Pattern(regexp = "[\\w -\\.,:;\\+_\\(\\)\\[\\]\\{\\}]+", message = "The name must follow the pattern [a-zA-Z0-9 -.,:;+()[]{}]")
    @Column(name = "name")
    private String name;

    @Column(name = "n_rows")
    private Long nRows;

    @Column(name = "n_columns")
    private Long nColumns;

    @OneToMany(mappedBy = "room")
    private List<Event> events;

    @OneToMany(mappedBy = "room")
    private List<Seat> seats;

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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
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

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
