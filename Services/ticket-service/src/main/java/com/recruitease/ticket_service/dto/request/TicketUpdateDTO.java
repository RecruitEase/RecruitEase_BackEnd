package com.recruitease.ticket_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketUpdateDTO {

    private int ticketId;
    private String delay;
    private String subject;
    private String type;
    private String jobTitle;
    private String date;
    private String status;
    private String description;
}
