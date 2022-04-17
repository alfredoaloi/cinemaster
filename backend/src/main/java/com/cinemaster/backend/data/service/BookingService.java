package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.SeatDto;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    void save(BookingDto bookingDto);

    void update(BookingDto bookingDto);

    void delete(BookingDto bookingDto);

    Optional<BookingDto> findById(Long id);

    List<BookingDto> findAll();

    void deleteExpired();

    List<SeatDto> findBookedSeatsByEventId(Long id);

    List<BookingDto> findAllByCashier();

}
