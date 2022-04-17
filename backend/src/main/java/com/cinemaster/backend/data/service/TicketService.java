package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.TicketDto;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    void save(TicketDto ticketDto);

    void update(TicketDto ticketDto);

    void delete(TicketDto ticketDto);

    List<TicketDto> findAll();

    Optional<TicketDto> findByBookingId(Long id);

    Optional<TicketDto> findByBarcode(String barcode);

    List<TicketDto> findAllByUserId(Long id);
}
