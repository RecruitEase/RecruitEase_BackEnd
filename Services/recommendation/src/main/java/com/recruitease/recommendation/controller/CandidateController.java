package com.recruitease.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.recommendation.DTO.ApplicationResponse;
import com.recruitease.recommendation.feign.ApplicationService;


@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/rank/{jobId}")
    public  ResponseEntity<ApplicationResponse> rankCandidates(@PathVariable("jobId") String jobId) {
    return applicationService.getApplicationsPerJob(jobId);
    }
}
