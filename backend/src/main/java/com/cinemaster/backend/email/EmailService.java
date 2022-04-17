package com.cinemaster.backend.email;

import com.cinemaster.backend.data.dto.CouponDto;
import com.cinemaster.backend.data.dto.TicketDto;

import java.io.File;

public interface EmailService {

    String EMAIL_ADDRESS = "alfredoaloi.test@gmail.com";
    String PATH_TO_BOOKING_CONFIRMED_TEMPLATE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "BookingConfirmed.html";
    String PATH_TO_BOOKING_DELETED_TEMPLATE = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "BookingDeleted.html";

    void sendTicketEmail(String to, TicketDto ticketDto, String pathToAttachment);

    void sendCouponEmail(String to, Long bookingId, CouponDto coupon);

}
