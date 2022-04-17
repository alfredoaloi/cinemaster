package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CashierDao extends JpaRepository<Cashier, Long>, JpaSpecificationExecutor<Cashier> {
}
