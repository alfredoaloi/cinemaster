package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AdminDao extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {
}
