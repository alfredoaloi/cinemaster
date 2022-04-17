package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

public interface EventDao extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    List<Event> findAllByShowIdAndDateAfter(Long id, LocalDate date);

    List<Event> findAllByDateAfterAndDateBefore(LocalDate start, LocalDate end);

    List<Event> findAllByShowId(Long id);

    List<Event> findAllByRoomIdAndDateAfter(Long id, LocalDate date);

    List<Event> findAllByDateBefore(LocalDate date);

}
