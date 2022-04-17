package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.data.dao.SeatDao;
import com.cinemaster.backend.data.dto.SeatDto;
import com.cinemaster.backend.data.entity.Seat;
import com.cinemaster.backend.data.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<SeatDto> findById(Long id) {
        Optional<Seat> optional = seatDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(seat -> modelMapper.map(seat, SeatDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<SeatDto> findAll() {
        return seatDao.findAll().stream().map(seat -> modelMapper.map(seat, SeatDto.class)).collect(Collectors.toList());
    }
}
