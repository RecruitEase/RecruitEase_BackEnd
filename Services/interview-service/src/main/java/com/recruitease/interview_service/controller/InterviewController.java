package com.recruitease.interview_service.controller;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.DTO.ResponseDTO;
import com.recruitease.interview_service.model.Interview;
import com.recruitease.interview_service.service.InterviewService;
import com.recruitease.interview_service.util.CodeList;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/interviews")
public class InterviewController {
    @Autowired
    InterviewService interviewService;
    @Autowired
    private ResponseDTO responseDTO;

    @GetMapping("/list")
    private String interviewsList(){
        return "interviews list";
    }

    @PostMapping("/create")
    private ResponseEntity<?> scheduleInterview(@Valid @RequestBody InterviewDTO interviewDTO){

        try{
            String res = interviewService.createInterview(interviewDTO);
            if(res.equals("00")){
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Interview created successfully");
                responseDTO.setContent(interviewDTO);
                return new ResponseEntity(responseDTO, HttpStatus.CREATED);

            }else if(res.equals("06")){
                responseDTO.setCode(CodeList.RSP_DUPLICATED);
                responseDTO.setMessage("Interview already exists");
                responseDTO.setContent(null);
                responseDTO.setErrors("Duplicate interview");
                return new ResponseEntity(responseDTO, HttpStatus.BAD_REQUEST);

            }else{
                responseDTO.setCode(CodeList.RSP_ERROR);
                responseDTO.setMessage("Error while creating interview");
                responseDTO.setContent(null);
                responseDTO.setErrors("Error");
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
