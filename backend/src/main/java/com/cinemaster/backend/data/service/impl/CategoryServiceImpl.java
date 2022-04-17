package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.ActorAlreadyPresentException;
import com.cinemaster.backend.core.exception.ActorNotFoundException;
import com.cinemaster.backend.core.exception.CategoryAlreadyPresentException;
import com.cinemaster.backend.core.exception.CategoryNotFoundException;
import com.cinemaster.backend.data.dao.CategoryDao;
import com.cinemaster.backend.data.dto.CategoryDto;
import com.cinemaster.backend.data.entity.Actor;
import com.cinemaster.backend.data.entity.Category;
import com.cinemaster.backend.data.entity.Show;
import com.cinemaster.backend.data.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(CategoryDto categoryDto) {
        List<Category> categories = categoryDao.findAllByName(categoryDto.getName());
        if (categories.isEmpty()) {
            Category category = modelMapper.map(categoryDto, Category.class);
            categoryDao.saveAndFlush(category);
            categoryDto.setId(category.getId());
        } else {
            throw new CategoryAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void update(CategoryDto categoryDto) {
        Optional<Category> optional = categoryDao.findById(categoryDto.getId());
        if (!(optional.isPresent())) {
            throw new CategoryNotFoundException();
        }
        List<Category> categories = categoryDao.findAllByName(categoryDto.getName());
        if (categories.isEmpty()) {
            Category category = modelMapper.map(categoryDto, Category.class);
            categoryDao.saveAndFlush(category);
        } else {
            throw new CategoryAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void delete(CategoryDto categoryDto) {
        Optional<Category> optional = categoryDao.findById(categoryDto.getId());
        if (optional.isPresent()) {
            Category category = optional.get();
            for (Show show : category.getShows()) {
                show.getCategories().remove(category);
            }
            categoryDao.delete(category);
        } else {
            throw new CategoryNotFoundException();
        }
    }

    @Override
    public Optional<CategoryDto> findById(Long id) {
        Optional<Category> optional = categoryDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(category -> modelMapper.map(category, CategoryDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }
}
