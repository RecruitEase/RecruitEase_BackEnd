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

import java.util.List;

@RestController
@RequestMapping("/api/v1/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;
    private final ResponseDTO responseDTO;

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_RECRUITER') or hasRole('ROLE_CANDIDATE')")
    public ResponseEntity getAllInterviews() {
        try {
            ResponseDTO responseDTO = interviewService.getInterview();

            if(responseDTO.getCode().equals(CodeList.RSP_NOT_AUTHORISED)){
                return new ResponseEntity(responseDTO, HttpStatus.UNAUTHORIZED);

            }else if(responseDTO.getCode().equals(CodeList.RSP_NO_DATA_FOUND)){
                return new ResponseEntity(responseDTO, HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity(responseDTO, HttpStatus.OK);
            }

        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error while retrieving interviews");
            responseDTO.setContent(null);
            responseDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_RECRUITER') or hasRole('ROLE_CANDIDATE')")
    public ResponseEntity getAllInterviewByID(@PathVariable String id) {
        try {
            ResponseDTO responseDTO = interviewService.getInterviewByID(id);

            if(responseDTO.getCode().equals(CodeList.RSP_NOT_AUTHORISED)){
                return new ResponseEntity(responseDTO, HttpStatus.UNAUTHORIZED);

            }else if(responseDTO.getCode().equals(CodeList.RSP_NO_DATA_FOUND)){
                return new ResponseEntity(responseDTO, HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity(responseDTO, HttpStatus.OK);
            }

        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error while retrieving interviews");
            responseDTO.setContent(null);
            responseDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> scheduleInterview(@RequestBody @Valid InterviewDTO interviewDTO){
        try {
            ResponseDTO responseDTO = interviewService.createInterview(interviewDTO);

            if(responseDTO.getCode().equals(CodeList.RSP_SUCCESS)){
                return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error while creating interview");
            responseDTO.setContent(null);
            responseDTO.setErrors(e.getMessage());
            return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
