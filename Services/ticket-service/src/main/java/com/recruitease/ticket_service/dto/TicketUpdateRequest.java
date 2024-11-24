package com.recruitease.ticket_service.dto;

import com.recruitease.ticket_service.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketUpdateRequest{
    Ticket.TicketStatus status;
    String note;
}

