package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RoomDao extends JpaRepository<Room, Long>, JpaSpecificationExecutor<Room> {

    List<Room> findAllByNameContains(String name);

    List<Room> findAllByName(String name);

}
