package com.recruitease.ticket_service.service;

import com.recruitease.ticket_service.config.CustomUserDetails;
import com.recruitease.ticket_service.dto.ResponseDTO;
import com.recruitease.ticket_service.dto.TicketRequest;
import com.recruitease.ticket_service.dto.TicketUpdateRequest;
import com.recruitease.ticket_service.entity.Ticket;
import com.recruitease.ticket_service.repository.TicketRepo;
import com.recruitease.ticket_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class TicketServiceNew {
    private final ModelMapper modelMapper;
    private final TicketRepo repository;

    public ResponseDTO createTicket(TicketRequest request) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();

        try{
            //get canidate id or recruiter id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String role=userDetails.getRole();




            //mapping
            Ticket ticket=modelMapper.map(request,Ticket.class);
            ticket.setStatus(Ticket.TicketStatus.UNDER_REVIEW);
            if(role.equalsIgnoreCase("recruiter")){
                ticket.setCreatorId(userDetails.getRecruiterDetails().getRecruiterId());
                ticket.setCreatorRole(Ticket.CreatorRole.RECRUITER);
            }else{
                ticket.setCreatorId(userDetails.getCandidateDetails().getCandidateId());
                ticket.setCreatorRole(Ticket.CreatorRole.CANDIDATE);
            }
            //saving to db
            var res=repository.save(ticket);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Ticket created successfully");
            responseDTO.setContent(res.getTicketId());
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            e.printStackTrace();
        }

        return responseDTO;
    }


    public ResponseDTO getTicket(String ticketId) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


        if(repository.existsById(ticketId)){
            Ticket res=repository.findById(ticketId).orElse(null);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }else{
            errors.put("ticket","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }

    public ResponseDTO getRecruiterTickets(String recruiterId) {
        var responseDTO=new ResponseDTO();
        try {
            var res = repository.findByCreatorRoleAndCreatorId(Ticket.CreatorRole.RECRUITER,recruiterId);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }

    public ResponseDTO getCandidateTickets(String candidateId) {
        var responseDTO=new ResponseDTO();
        try {
            var res = repository.findByCreatorRoleAndCreatorId(Ticket.CreatorRole.CANDIDATE,candidateId);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }


    public ResponseDTO updateTicket(String ticketId, TicketUpdateRequest request) {
        var responseDTO=new ResponseDTO();
        try {
            var res=repository.findById(ticketId);
            if (res.isPresent()) {
                    Ticket old=res.get();
                    if(request.getStatus()!=null)old.setStatus(request.getStatus());
                    if(request.getNote()!=null)old.setNote(request.getNote());
                    repository.save(old);

                    responseDTO.setCode(CodeList.RSP_SUCCESS);
                    responseDTO.setMessage("Success");

            } else {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not found!");
                responseDTO.setErrors(null);
            }
        }catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO getAllTickets() {
        var responseDTO=new ResponseDTO();
        try {
            var res = repository.findAll();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }


        return responseDTO;
    }
}