package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    void save(CategoryDto categoryDto);

    void update(CategoryDto categoryDto);

    void delete(CategoryDto categoryDto);

    Optional<CategoryDto> findById(Long id);

    List<CategoryDto> findAll();
}
