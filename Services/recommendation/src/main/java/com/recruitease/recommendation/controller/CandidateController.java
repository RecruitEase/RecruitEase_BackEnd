package com.recruitease.recommendation.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.recommendation.DTO.ApplicationResponse;
import com.recruitease.recommendation.DTO.ApplicationResponseContent;
import com.recruitease.recommendation.DTO.CvResponse;
import com.recruitease.recommendation.feign.ApplicationService;
import com.recruitease.recommendation.feign.CvService;

@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    private final ApplicationService applicationService;
    private final CvService cvService;

    @Autowired
    public CandidateController(ApplicationService applicationService, CvService cvService) {
        this.applicationService = applicationService;
        this.cvService = cvService;
    }

    @GetMapping("/rank/{jobId}")
    public ResponseEntity<CvResponse> rankCandidates(@PathVariable("jobId") String jobId) {

        ResponseEntity<ApplicationResponse> applicationResponses = applicationService.getApplicationsPerJob(jobId);

        if (applicationResponses.getBody() == null || applicationResponses.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

        List<ApplicationResponseContent> applications = applicationResponses.getBody().getContent();

      
        List<String> cvIds = applications.stream()
                .map(ApplicationResponseContent::getCvId)
                .collect(Collectors.toList());

       
        ResponseEntity<CvResponse> cvResponse = cvService.getCvList(cvIds);

        if (cvResponse.getBody() == null || cvResponse.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

       
        return cvResponse;
    }
}
