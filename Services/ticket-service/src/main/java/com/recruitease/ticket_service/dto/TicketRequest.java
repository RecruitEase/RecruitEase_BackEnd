package com.recruitease.ticket_service.dto;

import com.recruitease.ticket_service.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class TicketRequest {

    private Ticket.TicketType type;
    private String subject;
    private String description;
    private String creatorId;
    private Ticket.CreatorRole creatorRole;

}
