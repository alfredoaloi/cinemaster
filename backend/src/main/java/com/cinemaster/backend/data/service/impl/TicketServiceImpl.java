package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.data.dao.BookingDao;
import com.cinemaster.backend.data.dao.TicketDao;
import com.cinemaster.backend.data.dto.BookingDto;
import com.cinemaster.backend.data.dto.TicketDto;
import com.cinemaster.backend.data.entity.Ticket;
import com.cinemaster.backend.data.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketDao ticketDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void save(TicketDto ticketDto) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketDao.saveAndFlush(ticket);
    }

    @Override
    public void update(TicketDto ticketDto) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketDao.saveAndFlush(ticket);
    }

    @Override
    public void delete(TicketDto ticketDto) {
        Ticket ticket = modelMapper.map(ticketDto, Ticket.class);
        ticketDao.delete(ticket);
    }

    @Override
    public List<TicketDto> findAll() {
        return ticketDao.findAll().stream().map(ticket -> modelMapper.map(ticket, TicketDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<TicketDto> findByBookingId(Long id) {
        Optional<Ticket> optional = ticketDao.findByBookingId(id);
        if (optional.isPresent()) {
            return optional.map(ticket -> modelMapper.map(ticket, TicketDto.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<TicketDto> findByBarcode(String barcode) {
        Optional<Ticket> optional = ticketDao.findByBarcode(barcode);
        if (optional.isPresent()) {
            return optional.map(ticket -> modelMapper.map(ticket, TicketDto.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<TicketDto> findAllByUserId(Long id) {
        List<TicketDto> ticketDtos = new ArrayList<>();
        for (BookingDto booking : bookingDao.findAllByUserIdAndPayed(id, true).stream().map(booking -> modelMapper.map(booking, BookingDto.class)).collect(Collectors.toList())) {
            TicketDto ticketDto = new TicketDto();
            ticketDto.setBookingId(booking.getId());
            ticketDto.setUserName(booking.getUser().getFirstName() + " " + booking.getUser().getLastName());
            ticketDto.setShowName(booking.getEvent().getShow().getName());
            ticketDto.setRoomName(booking.getEvent().getRoom().getName());
            ticketDto.setSeat(booking.getSeat().getRow() + booking.getSeat().getColumn() + " - " + booking.getSeat().getSeatType());
            ticketDto.setDate(booking.getEvent().getDate());
            ticketDto.setStartTime(booking.getEvent().getStartTime());
            ticketDto.setPrice(booking.getPrice());
            ticketDtos.add(ticketDto);
        }
        return ticketDtos;
    }
}
