package com.recruitease.ticket_service.service;

import com.recruitease.ticket_service.dto.TicketDTO;
import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;

import java.util.List;

public interface TicketService {
    String saveTicket(TicketDTO ticketDTO);

    String updateTicket(TicketUpdateDTO ticketUpdateDTO);


    TicketDTO getTicketById(int ticketid);

    List<TicketDTO> getAllTickets();
}
