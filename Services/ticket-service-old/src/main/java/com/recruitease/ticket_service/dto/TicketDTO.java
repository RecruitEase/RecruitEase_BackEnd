package com.recruitease.ticket_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketDTO {

    private int ticketId;
    private String delay;
    private String subject;
    private String type;
    private String jobTitle;
    private String date;
    private String status;
    private String name;
    private String description;
}
