package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.BookingsPresentException;
import com.cinemaster.backend.core.exception.EventsPresentException;
import com.cinemaster.backend.core.exception.RoomAlreadyPresentException;
import com.cinemaster.backend.core.exception.RoomNotFoundException;
import com.cinemaster.backend.data.dao.BookingDao;
import com.cinemaster.backend.data.dao.EventDao;
import com.cinemaster.backend.data.dao.RoomDao;
import com.cinemaster.backend.data.dao.SeatDao;
import com.cinemaster.backend.data.dto.RoomDto;
import com.cinemaster.backend.data.dto.SeatDto;
import com.cinemaster.backend.data.entity.Room;
import com.cinemaster.backend.data.entity.Seat;
import com.cinemaster.backend.data.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public void save(RoomDto roomDto) {
        List<Room> rooms = roomDao.findAllByName(roomDto.getName());
        if (rooms.isEmpty()) {
            Room room = modelMapper.map(roomDto, Room.class);
            roomDao.saveAndFlush(room);
            roomDto.setId(room.getId());

            for (SeatDto seatDto : roomDto.getSeats()) {
                Seat seat = modelMapper.map(seatDto, Seat.class);
                seat.setRoom(room);
                seatDao.save(seat);
                seatDto.setId(seat.getId());
            }
        } else {
            throw new RoomAlreadyPresentException();
        }
    }

    @Override
    @Transactional
    public void update(RoomDto roomDto) {
        Room room = roomDao.findById(roomDto.getId()).orElseThrow(() -> new RoomNotFoundException());
        if (!(bookingDao.findAllByEventRoomId(room.getId()).isEmpty())) {
            throw new BookingsPresentException();
        }
        for (SeatDto seatDto : roomDto.getSeats()) {
            if (seatDto.getId() == null) {
                Seat seat = modelMapper.map(seatDto, Seat.class);
                seat.setRoom(room);
                seatDao.saveAndFlush(seat);
                seatDto.setId(seat.getId());
            }
        }
        for (Seat original : room.getSeats()) {
            boolean toDelete = true;
            for (SeatDto seatDto : roomDto.getSeats()) {
                if (original.getId().equals(seatDto.getId())) {
                    Seat seat = modelMapper.map(seatDto, Seat.class);
                    seat.setRoom(room);
                    seatDao.saveAndFlush(seat);
                    toDelete = false;
                    break;
                }
            }
            if (toDelete) {
                seatDao.delete(original);
            }
        }
        roomDao.saveAndFlush(modelMapper.map(roomDto, Room.class));
    }

    @Override
    @Transactional
    public void delete(RoomDto roomDto) {
        Room room = roomDao.findById(roomDto.getId()).orElseThrow(() -> new RoomNotFoundException());
        if (!(eventDao.findAllByRoomIdAndDateAfter(room.getId(), LocalDate.now()).isEmpty())) {
            throw new EventsPresentException();
        }
        for (Seat seat : room.getSeats()) {
            seatDao.delete(seat);
        }
        roomDao.delete(room);
    }

    @Override
    @Transactional
    public Optional<RoomDto> findById(Long id) {
        Optional<Room> optional = roomDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(room -> modelMapper.map(room, RoomDto.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(room -> modelMapper.map(room, RoomDto.class)).collect(Collectors.toList());
    }
}
