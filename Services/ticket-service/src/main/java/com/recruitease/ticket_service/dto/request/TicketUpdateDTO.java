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
    private String date;
    private String status;

}
