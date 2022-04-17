package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.EventDto;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Optional<EventDto> save(EventDto eventDto);

    void update(EventDto eventDto);

    void delete(EventDto eventDto);

    Optional<EventDto> findById(Long id);

    List<EventDto> findAll();

    List<EventDto> findAllByShowIdAfterNow(Long id);

    List<EventDto> findAllByShowId(Long id);

    void deleteOld();

    List<EventDto> findAllByToday();

}
