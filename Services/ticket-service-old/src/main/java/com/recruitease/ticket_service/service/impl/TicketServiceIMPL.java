//package com.recruitease.ticket_service.service.impl;
//
//import com.recruitease.ticket_service.dto.TicketDTO;
//import com.recruitease.ticket_service.dto.request.TicketUpdateDTO;
//import com.recruitease.ticket_service.entity.Ticket;
//import com.recruitease.ticket_service.repository.TicketRepo;
//import com.recruitease.ticket_service.service.TicketService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DuplicateKeyException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class TicketServiceIMPL implements TicketService {
//    @Autowired
//    private TicketRepo  ticketRepo;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public String saveTicket(TicketDTO ticketDTO) {
//        Ticket ticket =modelMapper.map(ticketDTO,Ticket.class);
//        if(!ticketRepo.existsById(ticket.getTicketId())){
//            ticketRepo.save(ticket);
//            return ("Save succesfully");
//        }else{
//            throw new DuplicateKeyException("Already added");
//        }
//    }
//
//    @Override
//    public String updateTicket(TicketUpdateDTO ticketUpdateDTO) {
//        if (ticketRepo.existsById(ticketUpdateDTO.getTicketId())) {
//            Ticket ticket = ticketRepo.getReferenceById(ticketUpdateDTO.getTicketId());
//            modelMapper.map(ticketUpdateDTO, ticket);
//            ticketRepo.save(ticket);
//            return "Updated Success";
//        } else {
//            throw new RuntimeException("No data found");
//        }
//    }
//
//    @Override
//    public TicketDTO getTicketById(int ticketid) {
//        if (ticketRepo.existsById(ticketid)) {
//            Ticket ticket = ticketRepo.getReferenceById(ticketid);
//            TicketDTO ticketDTO = modelMapper.map(ticket, TicketDTO.class);
//
//            return ticketDTO;
//        } else {
//            throw new RuntimeException("No Ticket");
//        }
//    }
//
//    @Override
//    public List<TicketDTO> getAllTickets() {
//        List<Ticket> getAllItems = ticketRepo.findAll();
//
//        // Corrected to use the variable name getAllItems
//        List<TicketDTO> ticketDTOList = getAllItems.stream()
//                .map(ticket -> modelMapper.map(ticket, TicketDTO.class))
//                .collect(Collectors.toList());
//
//        return ticketDTOList;
//    }
//
//}
//
