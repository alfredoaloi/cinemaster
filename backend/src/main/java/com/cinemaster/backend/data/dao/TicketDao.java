package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TicketDao extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    Optional<Ticket> findByBookingId(Long id);

    Optional<Ticket> findByBarcode(String barcode);
}
