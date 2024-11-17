package com.recruitease.ticket_service.controller;

import com.recruitease.ticket_service.dto.TicketDTO;
import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;
import com.recruitease.ticket_service.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping(
            path = {"/save"}
    )
    public String  saveTicket(@RequestBody TicketDTO ticketDTO){
        String message = ticketService.saveTicket(ticketDTO);
         return message;
    }

    @PutMapping("/update")
    public String updateTicket(@RequestBody TicketUpdateDTO ticketUpdateDTO){
        ticketService.updateTicket(ticketUpdateDTO);
        return ("updated");
    }

    @GetMapping (
            path = "/get-by-id",
            params = "id"
    )
    public TicketDTO getTicketById(@RequestParam(value = "id") int ticketid){
        TicketDTO ticketDTOS = ticketService.getTicketById(ticketid);
        return ticketDTOS;

    }

    @GetMapping(
            path = "/get-all-tickets"
    )
    public List<TicketDTO> getAllTickets() {
        List<TicketDTO> allTickets = ticketService.getAllTickets();
        return allTickets;
    }
}


