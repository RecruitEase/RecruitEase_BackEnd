package com.recruitease.ticket_service.controller;

import com.recruitease.ticket_service.dto.ResponseDTO;
import com.recruitease.ticket_service.dto.TicketDTO;
import com.recruitease.ticket_service.dto.TicketRequest;
import com.recruitease.ticket_service.dto.TicketUpdateRequest;
import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;
import com.recruitease.ticket_service.service.TicketServiceNew;
import com.recruitease.ticket_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/ticket")
@RequiredArgsConstructor
public class TicketController {

//    private final TicketServiceNew ticketService;

//    @PostMapping(
//            path = {"/save"}
//    )
//    public String  saveTicket(@RequestBody TicketDTO ticketDTO){
//        String message = ticketService.saveTicket(ticketDTO);
//         return message;
//    }
//
//    @PutMapping("/update")
//    public String updateTicket(@RequestBody TicketUpdateDTO ticketUpdateDTO){
//        ticketService.updateTicket(ticketUpdateDTO);
//        return ("updated");
//    }
//
//    @GetMapping (
//            path = "/get-by-id",
//            params = "id"
//    )
//    public TicketDTO getTicketById(@RequestParam(value = "id") int ticketid){
//        TicketDTO ticketDTOS = ticketService.getTicketById(ticketid);
//        return ticketDTOS;
//
//    }
//
//    @GetMapping(
//            path = "/get-all-tickets"
//    )
//    public List<TicketDTO> getAllTickets() {
//        List<TicketDTO> allTickets = ticketService.getAllTickets();
//        return allTickets;
//    }

    private final TicketServiceNew ticketService;


    //create ticket
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE','ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> createTicket(@RequestBody TicketRequest request){
        ResponseDTO res=ticketService.createTicket(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get a ticket
    @GetMapping("/view/{ticketId}")
    public ResponseEntity<ResponseDTO> getTicket(@PathVariable String ticketId){
        ResponseDTO res=ticketService.getTicket(ticketId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get all tickets recruiter
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<ResponseDTO> getRecruiterTicket(@PathVariable String recruiterId){
        ResponseDTO res=ticketService.getRecruiterTickets(recruiterId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get all tickets candidate
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> getCandidateTicket(@PathVariable String candidateId){
        ResponseDTO res=ticketService.getCandidateTickets(candidateId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }


    //update ticket
    //update application for  given applicationid
    @PutMapping("/update/{ticketId}")
    public ResponseEntity<ResponseDTO> updateTicket(@PathVariable String ticketId,@RequestBody TicketUpdateRequest request){
        ResponseDTO res= ticketService.updateTicket(ticketId,request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{//some error
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

}


