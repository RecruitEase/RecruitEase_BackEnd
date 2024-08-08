package com.recruitease.application_service.controller;

import com.recruitease.application_service.DTO.ApplicationRequest;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.config.CustomUserDetails;
import com.recruitease.application_service.service.ApplicationService;
import com.recruitease.application_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  String adminDetails(){
        return "admin";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public  String moderatorDetails(){
        return "moderator";
    }

    @GetMapping("/candidate")
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    public  String candidateDetails(){
        return "candidate";
    }

    @GetMapping("/recruiter")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public  String recruiterDetails(){
        return "recruiter";
    }

    //example of use=ing security context to get user details
    @GetMapping("/user")
    public String getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();
        String roles = userDetails.getAuthorities().toString();

        String additionalInfo;
        if(userDetails.getRole().equals("candidate")){
            additionalInfo= userDetails.getCandidateDetails().getCandidateId();
        }else if(userDetails.getRole().equals("recruiter")) {
            additionalInfo = userDetails.getRecruiterDetails().getRecruiterId();
        }else if(userDetails.getRole().equals("admin")) {
            additionalInfo = userDetails.getAdminDetails().getAdminId();
        } else{
            additionalInfo = userDetails.getModeratorDetails().getModeratorId();
        }

        return "User ID: " + userId + ", Roles: " + roles + ", Additional Info: " + (additionalInfo != null ? additionalInfo : "N/A");
    }

    //create new application
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createApplication(@RequestBody @Valid ApplicationRequest request){
        ResponseDTO res= applicationService.createApplication(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get application for the given application id


    //get applciations for a given candidate id

    //get applications for a given job id



    //update application for  given id or list of ids


    //withdraw application


}
