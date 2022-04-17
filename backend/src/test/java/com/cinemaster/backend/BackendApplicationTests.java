package com.cinemaster.backend;

import com.cinemaster.backend.service.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountServiceTest.class,
        ActorServiceTest.class,
        CategoryServiceTest.class,
        DirectorServiceTest.class,
        BookingServiceTest.class,
        CouponServiceTest.class,
        EventServiceTest.class,
        RoomServiceTest.class,
        ShowServiceTest.class,
        TicketServiceTest.class,
})
class BackendApplicationTests {
}
