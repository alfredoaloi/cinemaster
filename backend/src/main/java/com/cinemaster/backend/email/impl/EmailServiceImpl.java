package com.cinemaster.backend.email.impl;

import com.cinemaster.backend.data.dto.CouponDto;
import com.cinemaster.backend.data.dto.TicketDto;
import com.cinemaster.backend.email.EmailService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendTicketEmail(String to, TicketDto ticketDto, String pathToAttachment) {
        try { 
            byte[] encoded = Files.readAllBytes(Paths.get(PATH_TO_BOOKING_CONFIRMED_TEMPLATE));
            String text = new String(encoded, StandardCharsets.UTF_8);

            Document document = Jsoup.parse(text);

            Element bookingConfirmed = document.getElementById("booking-confirmed");
            bookingConfirmed.appendText(String.format("Prenotazione #%d confermata", ticketDto.getBookingId()));

            Element intro = document.getElementById("intro");
            intro.prepend(String.format("Gentile %s,<br>" +
                    "Con la presente la vogliamo informare della confermata prenotazione del biglietto:<br><br>", ticketDto.getUserName()));

            Element details = document.getElementById("table");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 24px\">" +
                    "<td>" + ticketDto.getShowName() + "</td>" +
                    "</tr>");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 16px\">" +
                    "<td>" + ticketDto.getRoomName() + "</td>" +
                    "</tr>");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 16px\">" +
                    "<td>" + ticketDto.getSeat() + " - " + String.format("€%.2f", ticketDto.getPrice()) + "</td>" +
                    "</tr>");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 16px\">" +
                    "<td>" + ticketDto.getDate().toString() + " - " + ticketDto.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + "</td>" +
                    "</tr>");

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(EMAIL_ADDRESS);
            helper.setTo(to);
            String subject = String.format("Prenotazione #%d confermata", ticketDto.getBookingId());
            helper.setSubject(subject);
            helper.setText(document.toString(), true);

            FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
            helper.addAttachment("Ticket.pdf", file);

            emailSender.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendCouponEmail(String to, Long bookingId, CouponDto coupon) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(PATH_TO_BOOKING_DELETED_TEMPLATE));
            String text = new String(encoded, StandardCharsets.UTF_8);

            Document document = Jsoup.parse(text);

            Element bookingConfirmed = document.getElementById("booking-deleted");
            bookingConfirmed.appendText(String.format("Prenotazione #%d cancellata", bookingId));

            Element intro = document.getElementById("intro");
            intro.prepend(String.format("Gentile %s,<br>" +
                    "Con la presente la vogliamo informare della cancellazione della prenotazione #%d.<br>" +
                    "Di seguito, può trovare il codice del coupon, che può utilizzare qualora voglia effettuare una nuova prenotazione:<br><br>", coupon.getUser().getFirstName() + " " + coupon.getUser().getLastName(), bookingId));

            Element details = document.getElementById("table");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 24px\">" +
                    "<td>" + coupon.getCode() + "</td>" +
                    "</tr>");
            details.append("<tr align=\"center\" bgcolor=\"#ffffff\" style=\"color: black; font-family: Bahnschrift, sans-serif; font-size: 16px\">" +
                    "<td>" + String.format("Valore: €%.2f", coupon.getValue()) + "</td>" +
                    "</tr>");

            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(EMAIL_ADDRESS);
            helper.setTo(to);
            String subject = String.format("Prenotazione #%d cancellata", bookingId);
            helper.setSubject(subject);
            helper.setText(document.toString(), true);

            emailSender.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
