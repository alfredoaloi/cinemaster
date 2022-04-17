package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.ActorDto;
import com.cinemaster.backend.data.entity.Actor;

import java.util.List;
import java.util.Optional;

public interface ActorService {

    void save(ActorDto actorDto);

    void update(ActorDto actorDto);

    void delete(ActorDto actorDto);

    Optional<ActorDto> findById(Long id);

    List<ActorDto> findAll();
}
