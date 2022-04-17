package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.ShowDto;
import com.cinemaster.backend.data.specification.ShowSpecification;

import java.util.List;
import java.util.Optional;

public interface ShowService {

    void save(ShowDto showDto);

    void update(ShowDto showDto);

    void delete(ShowDto showDto);

    Optional<ShowDto> findById(Long id);

    List<ShowDto> findAll();

    List<ShowDto> findAllByFilter(ShowSpecification.Filter filter);

    List<ShowDto> findAllByEventBeforeNextWeek();
}
