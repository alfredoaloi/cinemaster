package com.cinemaster.backend.service;

import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.service.*;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    ActorService actorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DirectorService directorService;

    @Autowired
    TicketService ticketService;

    @Autowired
    BookingService bookingService;

    @Autowired
    RoomService roomService;

    @Autowired
    EventService eventService;

    @Autowired
    ShowService showService;

    @Autowired
    CouponService couponService;

    @Autowired
    ModelMapper modelMapper;

}
