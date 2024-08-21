package com.recruitease.ticket_service.service.impl;

import com.recruitease.ticket_service.dto.TicketDTO;
import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;
import com.recruitease.ticket_service.entity.Ticket;
import com.recruitease.ticket_service.repository.TicketRepo;
import com.recruitease.ticket_service.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceIMPL implements TicketService {
    @Autowired
    private TicketRepo  ticketRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveTicket(TicketDTO ticketDTO) {
        Ticket ticket =modelMapper.map(ticketDTO,Ticket.class);
        if(!ticketRepo.existsById(ticket.getTicketId())){
            ticketRepo.save(ticket);
//            return ticket.getTicketId()+"Save succesfully";
            return ("Save succesfully");
        }else{
            throw new DuplicateKeyException("Already added");
        }
    }

    @Override
    public String updateTicket(TicketUpdateDTO ticketUpdateDTO) {
        if(ticketRepo.existsById(ticketUpdateDTO.getTicketId())){
            Ticket ticket = ticketRepo.getReferenceById(ticketUpdateDTO.getTicketId());
            ticket.setDelay(ticketUpdateDTO.getDelay());
            ticket.setSubject(ticketUpdateDTO.getSubject());
            ticket.setType(ticketUpdateDTO.getType());
            ticket.setJobTitle(ticketUpdateDTO.getJobTitle());
            ticket.setDate(ticketUpdateDTO.getDate());
            ticket.setStatus(ticketUpdateDTO.getStatus());
            ticket.setDescription(ticketUpdateDTO.getDescription());

            ticketRepo.save(ticket);
            return ("Updated Success");
        }else {
            throw new RuntimeException("No data found");
        }
    }

}