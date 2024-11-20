package com.recruitease.ats.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.ats.DTO.RequestDTO;
import com.recruitease.ats.DTO.ResponseDTO;

@RestController
@RequestMapping("/api/v1/ats")
public class AtsController {

    @PostMapping("/history")
    public ResponseEntity<ResponseDTO> getHistory(@RequestBody RequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("History of candidate with id " + requestDTO.getCandidateId() + 
                                " requested by recruiter with id " + requestDTO.getRecruiterId());
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
