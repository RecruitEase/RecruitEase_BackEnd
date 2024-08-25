package com.recruitease.auth_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recruitease.auth_service.DTO.*;
import com.recruitease.auth_service.DTO.LoggedUser.*;
import com.recruitease.auth_service.config.CustomUserDetails;
import com.recruitease.auth_service.service.AuthService;
import com.recruitease.auth_service.service.UserService;
import com.recruitease.auth_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDetailsController {

    private final ModelMapper modelMapper;
    private final UserService userService;



    @PostMapping({"/detail-list", "/recruiter-list","/admin-list","/moderator-list","/candidate-list"})
    public ResponseEntity<ResponseDTO> userDetailList(@RequestBody UserDetailsRequestDTO request) {
        ResponseDTO res= userService.getUserDetailsLists(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }





    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> candidateDetails(@PathVariable  String candidateId) {
        ResponseDTO res= userService.getCandidateDetails(candidateId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<ResponseDTO> recruiterDetails(@PathVariable  String recruiterId) {
        ResponseDTO res= userService.getRecruiterDetails(recruiterId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/moderator/{moderatorId}")
    public ResponseEntity<ResponseDTO> moderatorDetails(@PathVariable  String moderatorId) {
        ResponseDTO res= userService.getModeratorDetails(moderatorId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<ResponseDTO> adminDetails(@PathVariable  String adminId) {
        ResponseDTO res= userService.getAdminDetails(adminId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

}
