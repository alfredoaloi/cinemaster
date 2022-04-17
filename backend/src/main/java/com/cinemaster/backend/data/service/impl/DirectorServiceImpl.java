package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.CategoryNotFoundException;
import com.cinemaster.backend.core.exception.DirectorAlreadyPresentException;
import com.cinemaster.backend.core.exception.DirectorNotFoundException;
import com.cinemaster.backend.data.dao.DirectorDao;
import com.cinemaster.backend.data.dto.DirectorDto;
import com.cinemaster.backend.data.entity.Director;
import com.cinemaster.backend.data.entity.Show;
import com.cinemaster.backend.data.service.DirectorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements DirectorService {

    @Autowired
    private DirectorDao directorDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(DirectorDto directorDto) {
        List<Director> directors = directorDao.findAllByName(directorDto.getName());
        if (directors.isEmpty()) {
            Director director = modelMapper.map(directorDto, Director.class);
            directorDao.saveAndFlush(director);
            directorDto.setId(director.getId());
        } else {
            throw new DirectorAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void update(DirectorDto directorDto) {
        Optional<Director> optional = directorDao.findById(directorDto.getId());
        if (!(optional.isPresent())) {
            throw new DirectorNotFoundException();
        }
        List<Director> directors = directorDao.findAllByName(directorDto.getName());
        if (directors.isEmpty()) {
            Director director = modelMapper.map(directorDto, Director.class);
            directorDao.saveAndFlush(director);
        } else {
            throw new DirectorAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void delete(DirectorDto directorDto) {
        Optional<Director> optional = directorDao.findById(directorDto.getId());
        if (optional.isPresent()) {
            Director director = optional.get();
            for (Show show : director.getShows()) {
                show.getDirectors().remove(director);
            }
            directorDao.delete(director);
        } else {
            throw new DirectorNotFoundException();
        }
    }

    @Override
    public Optional<DirectorDto> findById(Long id) {
        Optional<Director> optional = directorDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(director -> modelMapper.map(director, DirectorDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<DirectorDto> findAll() {
        return directorDao.findAll().stream().map(director -> modelMapper.map(director, DirectorDto.class)).collect(Collectors.toList());
    }
}
