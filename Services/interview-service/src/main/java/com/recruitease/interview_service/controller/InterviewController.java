package com.recruitease.interview_service.controller;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.DTO.ResponseDTO;
import com.recruitease.interview_service.service.InterviewService;
import com.recruitease.interview_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;
    private  ResponseDTO responseDTO;


    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    private String interviewsList2(){
        return "interviews list";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    private ResponseEntity<ResponseDTO> scheduleInterview(@RequestBody @Valid InterviewDTO interviewDTO){

        try{
                responseDTO  = interviewService.createInterview(interviewDTO);


            if(responseDTO.getCode().equals("00")){;
                return new ResponseEntity(responseDTO, HttpStatus.CREATED);

            }else{
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
            }

        }catch(Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error while creating interview");
            responseDTO.setContent(null);
            responseDTO.setErrors("Error");
            return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
