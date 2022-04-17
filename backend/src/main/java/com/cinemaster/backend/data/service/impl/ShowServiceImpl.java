package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.ActorNotFoundException;
import com.cinemaster.backend.core.exception.CategoryNotFoundException;
import com.cinemaster.backend.core.exception.DirectorNotFoundException;
import com.cinemaster.backend.core.exception.ShowNotFoundException;
import com.cinemaster.backend.data.dao.*;
import com.cinemaster.backend.data.dto.ActorDto;
import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.dto.DirectorDto;
import com.cinemaster.backend.data.dto.ShowDto;
import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Show;
import com.cinemaster.backend.data.service.ShowService;
import com.cinemaster.backend.data.specification.ShowSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShowServiceImpl implements ShowService {

    @Autowired
    private ShowDao showDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ActorDao actorDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private DirectorDao directorDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(ShowDto showDto) {
        for (ActorDto actor : showDto.getActors()) {
            if (!(actorDao.findById(actor.getId()).isPresent())) {
                throw new ActorNotFoundException();
            }
        }
        for (CategoryDto category : showDto.getCategories()) {
            if (!(categoryDao.findById(category.getId()).isPresent())) {
                throw new CategoryNotFoundException();
            }
        }
        for (DirectorDto director : showDto.getDirectors()) {
            if (!(directorDao.findById(director.getId()).isPresent())) {
                throw new DirectorNotFoundException();
            }
        }
        Show show = modelMapper.map(showDto, Show.class);
        showDao.saveAndFlush(show);
        showDto.setId(show.getId());
    }

    @Override
    @Transactional
    public void update(ShowDto showDto) {
        for (ActorDto actor : showDto.getActors()) {
            if (!(actorDao.findById(actor.getId()).isPresent())) {
                throw new ActorNotFoundException();
            }
        }
        for (CategoryDto category : showDto.getCategories()) {
            if (!(categoryDao.findById(category.getId()).isPresent())) {
                throw new CategoryNotFoundException();
            }
        }
        for (DirectorDto director : showDto.getDirectors()) {
            if (!(directorDao.findById(director.getId()).isPresent())) {
                throw new DirectorNotFoundException();
            }
        }
        Show show = modelMapper.map(showDto, Show.class);
        showDao.saveAndFlush(show);
    }

    @Override
    @Transactional
    public void delete(ShowDto showDto) {
        Show show = modelMapper.map(showDto, Show.class);
        showDao.delete(show);
    }

    @Override
    public Optional<ShowDto> findById(Long id) {
        Optional<Show> optional = showDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(show -> modelMapper.map(show, ShowDto.class));
        } else {
            throw new ShowNotFoundException();
        }
    }

    @Override
    @Transactional
    public List<ShowDto> findAll() {
        return showDao.findAll().stream().map(show -> modelMapper.map(show, ShowDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ShowDto> findAllByFilter(ShowSpecification.Filter filter) {
        return showDao.findAll(ShowSpecification.findAllByFilter(filter)).stream().map(show -> modelMapper.map(show, ShowDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ShowDto> findAllByEventBeforeNextWeek() {
        List<Show> shows = new ArrayList<>();
        for (Event event : eventDao.findAllByDateAfterAndDateBefore(LocalDate.now(), LocalDate.now().plusDays(7))) {
            if (!(shows.contains(event.getShow()))) {
                shows.add(event.getShow());
            }
        }
        return shows.stream().map(show -> modelMapper.map(show, ShowDto.class)).collect(Collectors.toList());
    }
}
