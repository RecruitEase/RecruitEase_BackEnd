package com.recruitease.joblisting.controller;

import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobsController {

    private final JobService jobService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createJob(@RequestBody JobRequest jobRequest) {
        jobService.createJob(jobRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<JobResponse> getAllJobs() {
        return jobService.getAllJobs();
    }
}
