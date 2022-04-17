package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SeatDao extends JpaRepository<Seat, Long>, JpaSpecificationExecutor<Event> {

}
