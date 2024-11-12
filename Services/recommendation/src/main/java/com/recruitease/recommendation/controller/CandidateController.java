package com.recruitease.recommendation.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.recommendation.DTO.ApplicationResponse;
import com.recruitease.recommendation.DTO.ApplicationResponseContent;
import com.recruitease.recommendation.DTO.CvResponse;
import com.recruitease.recommendation.DTO.CvResponseContent;
import com.recruitease.recommendation.DTO.UserDetailsRequestDTO;
import com.recruitease.recommendation.DTO.UserDetailsResponse;
import com.recruitease.recommendation.DTO.UserDetailsResponseContent;
import com.recruitease.recommendation.feign.ApplicationService;
import com.recruitease.recommendation.feign.CvService;
import com.recruitease.recommendation.feign.UserDetailService;
import com.recruitease.recommendation.service.CandidateRecommendation;

@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    private final ApplicationService applicationService;
    private final CvService cvService;
    private final UserDetailService userDetailService;
    private final CandidateRecommendation candidateRecommendation;

    @Autowired
    public CandidateController(
            ApplicationService applicationService,
            CvService cvService,
            UserDetailService userDetailService,
            CandidateRecommendation candidateRecommendation) {
        this.applicationService = applicationService;
        this.cvService = cvService;
        this.userDetailService = userDetailService;
        this.candidateRecommendation = candidateRecommendation;
    }

    @GetMapping("/rank/{jobId}")
    public ResponseEntity<Map<String, Double>> rankCandidates(@PathVariable("jobId") String jobId, @RequestParam List<String> keywords) {
        // Fetch applications for the specified job
        ResponseEntity<ApplicationResponse> applicationResponses = applicationService.getApplicationsPerJob(jobId);

        if (applicationResponses.getBody() == null || applicationResponses.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

        // Extract candidate and CV IDs from applications
        List<ApplicationResponseContent> applications = (List<ApplicationResponseContent>) applicationResponses.getBody().getContent();
        System.out.println("Applications: " + applications);
        
        List<String> candidateIds = applications.stream()
                .map(ApplicationResponseContent::getCandidateId)
                .collect(Collectors.toList());

        List<String> cvIds = applications.stream()
                .map(ApplicationResponseContent::getCvId)
                .collect(Collectors.toList());

        // Fetch candidate details from UserDetailService
        UserDetailsRequestDTO userDetailsRequest = new UserDetailsRequestDTO(null, candidateIds, null, null);
        ResponseEntity<UserDetailsResponse> userDetailsResponse = userDetailService.userDetailList(userDetailsRequest);

        List<UserDetailsResponseContent> candidateDetails = (List<UserDetailsResponseContent>) userDetailsResponse.getBody().getContent();
        System.out.println("Candidate details: " + candidateDetails);

        // Fetch CV details from CvService
        ResponseEntity<CvResponse> cvResponse = cvService.getCvList(cvIds);
        List<CvResponseContent> cvDetails = cvResponse.getBody().getContent();
        System.out.println("CV details: " + cvDetails);

        // Rank candidates using the CandidateRecommendation service
        // Map<String, Double> rankedCandidates = candidateRecommendation.rankCandidatesByKeywords(candidateDetails, cvDetails, keywords);

        Map<String, Double> rankedCandidates = null;
        return ResponseEntity.ok(rankedCandidates);
    }
}
