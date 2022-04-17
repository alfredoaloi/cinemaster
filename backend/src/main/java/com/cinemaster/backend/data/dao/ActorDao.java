package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ActorDao extends JpaRepository<Actor, Long>, JpaSpecificationExecutor<Actor> {

    List<Actor> findAllByNameContains(String name);

    List<Actor> findAllByName(String name);
}
