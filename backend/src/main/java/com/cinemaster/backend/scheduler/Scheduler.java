package com.cinemaster.backend.scheduler;

import com.cinemaster.backend.data.service.BookingService;
import com.cinemaster.backend.data.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EventService eventService;

    @Scheduled(fixedRate = 1000 * 60)
    public void checkBookingsExpiration() {
        bookingService.deleteExpired();
        eventService.deleteOld();
    }

}
