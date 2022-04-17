package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.DirectorDto;

import java.util.List;
import java.util.Optional;

public interface DirectorService {

    void save(DirectorDto directorDto);

    void update(DirectorDto directorDto);

    void delete(DirectorDto directorDto);

    Optional<DirectorDto> findById(Long id);

    List<DirectorDto> findAll();
}
