package com.recruitease.ticket_service.controller;

import com.recruitease.ticket_service.dto.TicketDTO;
import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;
import com.recruitease.ticket_service.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
@CrossOrigin
public class TicketController {

    @Autowired
    private TicketService ticketService;

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

//    @GetMapping (
//            path = "/get-by-id",
//            params = "id"
//    )
//    public TicketDTO getTicketById(@RequestParam(value = "id") int ticketid){
//        TicketDTO ticketDTO = ticketService.getTicketId(ticketid);
//        return ticketDTO;
//
//    }
}
