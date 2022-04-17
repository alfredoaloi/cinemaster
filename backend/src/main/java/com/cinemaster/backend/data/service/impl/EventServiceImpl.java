package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.*;
import com.cinemaster.backend.data.dao.BookingDao;
import com.cinemaster.backend.data.dao.EventDao;
import com.cinemaster.backend.data.dao.RoomDao;
import com.cinemaster.backend.data.dao.ShowDao;
import com.cinemaster.backend.data.dto.EventDto;
import com.cinemaster.backend.data.entity.Booking;
import com.cinemaster.backend.data.entity.Event;
import com.cinemaster.backend.data.entity.Room;
import com.cinemaster.backend.data.service.EventService;
import com.cinemaster.backend.data.specification.EventSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private ShowDao showDao;

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private BookingDao bookingDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Optional<EventDto> save(EventDto eventDto) {
        List<Event> events = eventDao.findAll(EventSpecification.findAllBy(modelMapper.map(eventDto.getRoom(), Room.class), eventDto.getDate(), eventDto.getStartTime(), eventDto.getEndTime()));
        if (events.isEmpty()) {
            Event event = modelMapper.map(eventDto, Event.class);
            eventDao.saveAndFlush(event);
            eventDto.setId(event.getId());
            return Optional.empty();
        } else {
            return Optional.of(eventDto);
        }
    }

    @Override
    @Transactional
    public void update(EventDto eventDto) {
        if (!(eventDao.findById(eventDto.getId()).isPresent())) {
            throw new EventNotFoundException();
        }
        if (!(showDao.findById(eventDto.getShow().getId()).isPresent())) {
            throw new ShowNotFoundException();
        }
        if (!(roomDao.findById(eventDto.getRoom().getId()).isPresent())) {
            throw new RoomNotFoundException();
        }
        if (bookingDao.findAllByEventId(eventDto.getId()).isEmpty()) {
            List<Event> events = eventDao.findAll(EventSpecification.findAllBy(modelMapper.map(eventDto.getRoom(), Room.class), eventDto.getDate(), eventDto.getStartTime(), eventDto.getEndTime()));
            if (events.isEmpty() || (events.size() == 1 && events.get(0).getId() == eventDto.getId())) {
                Event event = modelMapper.map(eventDto, Event.class);
                eventDao.saveAndFlush(event);
            } else {
                throw new EventAlreadyPresentException();
            }
        } else {
            throw new BookingsPresentException();
        }
    }

    @Override
    @Transactional
    public void delete(EventDto eventDto) {
        if (!(eventDao.findById(eventDto.getId()).isPresent())) {
            throw new EventNotFoundException();
        }
        if (bookingDao.findAllByEventId(eventDto.getId()).isEmpty()) {
            Event event = modelMapper.map(eventDto, Event.class);
            eventDao.delete(event);
        } else {
            throw new BookingsPresentException();
        }
    }

    @Override
    public Optional<EventDto> findById(Long id) {
        Optional<Event> optional = eventDao.findById(id);
        if (optional.isPresent()) {
            return optional.map(event -> modelMapper.map(event, EventDto.class));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public List<EventDto> findAll() {
        return eventDao.findAll().stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EventDto> findAllByShowIdAfterNow(Long id) {
        if (!(showDao.findById(id).isPresent())) {
            throw new ShowNotFoundException();
        }
        return eventDao.findAllByShowIdAndDateAfter(id, LocalDate.now()).stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<EventDto> findAllByShowId(Long id) {
        if (!(showDao.findById(id).isPresent())) {
            throw new ShowNotFoundException();
        }
        return eventDao.findAllByShowId(id).stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteOld() {
        for (Event event : eventDao.findAllByDateBefore(LocalDate.now().plusDays(1))) {
            if (!(event.getDate().isEqual(LocalDate.now()) && event.getEndTime().isAfter(LocalTime.now()))) {
                for (Booking booking : bookingDao.findAllByEventId(event.getId())) {
                    bookingDao.delete(booking);
                }
                eventDao.delete(event);
            }
        }
    }

    @Override
    @Transactional
    public List<EventDto> findAllByToday() {
        return eventDao.findAllByDateBefore(LocalDate.now().plusDays(1)).stream().map(event -> modelMapper.map(event, EventDto.class)).collect(Collectors.toList());
    }
}
