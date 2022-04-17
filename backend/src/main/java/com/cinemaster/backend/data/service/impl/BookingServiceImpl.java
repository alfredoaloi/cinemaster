package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.*;
import com.cinemaster.backend.data.dao.BookingDao;
import com.cinemaster.backend.data.dao.EventDao;
import com.cinemaster.backend.data.dao.RoomDao;
import com.cinemaster.backend.data.dao.SeatDao;
import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.SeatDto;
import com.cinemaster.backend.data.entity.Booking;
import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Room;
import com.cinemaster.backend.data.entity.Seat;
import com.cinemaster.backend.data.service.BookingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(BookingDto bookingDto) {
        Booking booking = modelMapper.map(bookingDto, Booking.class);
        Event event = eventDao.findById(booking.getEvent().getId()).orElseThrow(() -> new EventNotFoundException());
        Room room = roomDao.findById(event.getRoom().getId()).orElseThrow(() -> new RoomNotFoundException());
        Seat seat = seatDao.findById(booking.getSeat().getId()).orElseThrow(() -> new SeatNotFoundException());
        if (room.getId() != seat.getRoom().getId()) {
            throw new InvalidDataException();
        }
        List<Booking> bookings = bookingDao.findAllByEventAndSeat(booking.getEvent(), booking.getSeat());
        if (bookings.isEmpty()) {
            bookingDao.saveAndFlush(booking);
            bookingDto.setId(booking.getId());
        } else {
            throw new BookingAlreadyPresentException();
        }
    }

    @Override
    public void update(BookingDto bookingDto) {
        Booking booking = bookingDao.findById(bookingDto.getId()).orElseThrow(() -> new BookingNotFoundException());
        if (booking.getPayed()) {
            throw new BookingAlreadyPayedException();
        } else {
            booking = modelMapper.map(bookingDto, Booking.class);
            bookingDao.saveAndFlush(booking);
        }
    }

    @Override
    @Transactional
    public void delete(BookingDto bookingDto) {
        Optional<Booking> optional = bookingDao.findById(bookingDto.getId());
        if (optional.isPresent()) {
            Booking booking = optional.get();
            bookingDao.delete(booking);
        } else {
            throw new BookingNotFoundException();
        }
    }

    @Override
    @Transactional
    public Optional<BookingDto> findById(Long id) {
        Optional<Booking> optional = bookingDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(booking -> modelMapper.map(booking, BookingDto.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<BookingDto> findAll() {
        return bookingDao.findAll().stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteExpired() {
        for (Booking booking : bookingDao.findAllByExpirationBefore(LocalDateTime.now())) {
            bookingDao.delete(booking);
        }
    }

    @Override
    @Transactional
    public List<SeatDto> findBookedSeatsByEventId(Long id) {
        if (!(eventDao.findById(id).isPresent())) {
            throw new EventNotFoundException();
        }
        List<SeatDto> seats = new ArrayList<>();
        for (BookingDto booking : bookingDao.findAllByEventId(id).stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList())) {
            seats.add(booking.getSeat());
        }
        return seats;
    }

    @Override
    @Transactional
    public List<BookingDto> findAllByCashier() {
        return bookingDao.findAllByUserIdIsNull().stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList());
    }
}
