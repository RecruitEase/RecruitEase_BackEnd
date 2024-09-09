package com.recruitease.joblisting.controller;

import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.dto.Response;

import com.recruitease.joblisting.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Validated
public class JobsController {

    private final JobService jobService;


    @PostMapping
    public ResponseEntity<Response> createJob(@RequestBody JobRequest jobRequest) {
        Response response = jobService.createJob(jobRequest);
        if (response.getCode().equals("201")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

 
    @GetMapping
    public ResponseEntity<Response> getAllJobs() {
        Response response = jobService.getAllJobs();
        if (response.getCode().equals("200")) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
    }

   
}
