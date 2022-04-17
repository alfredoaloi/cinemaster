package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.ActorAlreadyPresentException;
import com.cinemaster.backend.core.exception.ActorNotFoundException;
import com.cinemaster.backend.core.exception.InvalidDataException;
import com.cinemaster.backend.data.dao.ActorDao;
import com.cinemaster.backend.data.dto.ActorDto;
import com.cinemaster.backend.data.entity.Actor;
import com.cinemaster.backend.data.entity.Show;
import com.cinemaster.backend.data.service.ActorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorDao actorDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(ActorDto actorDto) {
        List<Actor> actors = actorDao.findAllByName(actorDto.getName());
        if (actors.isEmpty()) {
            Actor actor = modelMapper.map(actorDto, Actor.class);
            actorDao.saveAndFlush(actor);
            actorDto.setId(actor.getId());
        } else {
            throw new ActorAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void update(ActorDto actorDto) {
        Optional<Actor> optional = actorDao.findById(actorDto.getId());
        if (!(optional.isPresent())) {
            throw new ActorNotFoundException();
        }
        List<Actor> actors = actorDao.findAllByName(actorDto.getName());
        if (actors.isEmpty()) {
            Actor actor = modelMapper.map(actorDto, Actor.class);
            actorDao.saveAndFlush(actor);
        } else {
            throw new ActorAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void delete(ActorDto actorDto) {
        Optional<Actor> optional = actorDao.findById(actorDto.getId());
        if (optional.isPresent()) {
            Actor actor = optional.get();
            for (Show show : actor.getShows()) {
                show.getActors().remove(actor);
            }
            actorDao.delete(actor);
        } else {
            throw new ActorNotFoundException();
        }
    }

    @Override
    public Optional<ActorDto> findById(Long id) {
        Optional<Actor> optional = actorDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(actor -> modelMapper.map(actor, ActorDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<ActorDto> findAll() {
        return actorDao.findAll().stream().map(actor -> modelMapper.map(actor, ActorDto.class)).collect(Collectors.toList());
    }
}
