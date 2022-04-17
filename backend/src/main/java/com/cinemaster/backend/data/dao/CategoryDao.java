package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    List<Category> findAllByNameContains(String name);

    List<Category> findAllByName(String name);
}
