package com.cinemaster.backend.pdf;

import com.cinemaster.backend.data.dto.TicketDto;

public interface PdfService {

    String createPdf(TicketDto ticketDto);

}
