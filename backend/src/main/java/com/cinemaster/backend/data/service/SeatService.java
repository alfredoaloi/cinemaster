package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.SeatDto;

import java.util.List;
import java.util.Optional;

public interface SeatService {

    Optional<SeatDto> findById(Long id);

    List<SeatDto> findAll();
}
