package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Booking;
import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Event> {

    List<Booking> findAllByEventAndSeat(Event event, Seat seat);

    List<Booking> findAllByExpirationBefore(LocalDateTime now);

    List<Booking> findAllByEventId(Long id);

    List<Booking> findAllByUserIdAndPayed(Long id, Boolean payed);

    List<Booking> findAllByEventRoomId(Long id);

    List<Booking> findAllByEventDateBefore(LocalDate date);

    List<Booking> findAllByUserIdIsNull();
}
