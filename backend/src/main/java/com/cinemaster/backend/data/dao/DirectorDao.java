package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface DirectorDao extends JpaRepository<Director, Long>, JpaSpecificationExecutor<Director> {

    List<Director> findAllByNameContains(String name);

    List<Director> findAllByName(String name);
}
