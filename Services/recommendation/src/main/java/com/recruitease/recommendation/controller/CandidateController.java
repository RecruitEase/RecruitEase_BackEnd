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
import com.recruitease.recommendation.DTO.CandidateData;
import com.recruitease.recommendation.DTO.CvResponse;
import com.recruitease.recommendation.DTO.CvResponseContent;
import com.recruitease.recommendation.DTO.RecommendationRequestDTO;
import com.recruitease.recommendation.DTO.ResponseDTO;
import com.recruitease.recommendation.DTO.UserDetailsRequestDTO;
import com.recruitease.recommendation.DTO.UserDetailsResponse;
import com.recruitease.recommendation.DTO.UserDetailsResponseContent;
import com.recruitease.recommendation.feign.ApplicationService;
import com.recruitease.recommendation.feign.CvService;
import com.recruitease.recommendation.feign.UserDetailService;
import com.recruitease.recommendation.service.CandidateRecommendation;
import com.recruitease.recommendation.service.PdfService;

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
    public ResponseEntity<ResponseDTO> rankCandidates(@PathVariable("jobId") String jobId, @RequestParam List<String> keywords) {
        // Fetch applications for the specified job
        ResponseEntity<ApplicationResponse> applicationResponse = applicationService.getApplicationsPerJob(jobId);

        if (applicationResponse.getBody() == null || applicationResponse.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

        // Extract candidate and CV IDs from applications
        List<ApplicationResponseContent> applications = applicationResponse.getBody().getContent();
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

        UserDetailsResponseContent candidateDetails = userDetailsResponse.getBody().getContent();
        List<CandidateData> candidateList = candidateDetails.getCandidateList();
        System.out.println("Candidate details: " + candidateList);

        // Fetch CV details from CvService
        ResponseEntity<CvResponse> cvResponse = cvService.getCvList(cvIds);
        List<CvResponseContent> cvDetails = cvResponse.getBody().getContent();
        System.out.println("CV details: " + cvDetails);

        List<RecommendationRequestDTO> recommendationRequestDTOList = candidateList.stream()
        .map(candidate -> {
            String cvId = cvDetails.stream()
                    .filter(cv -> cv.getCandidateId().equals(candidate.getCandidateId()))
                    .map(CvResponseContent::getCvId)
                    .findFirst()
                    .orElse(null);
            String file = cvDetails.stream()
                    .filter(cv -> cv.getCandidateId().equals(candidate.getCandidateId()))
                    .map(CvResponseContent::getFile)
                    .findFirst()
                    .orElse(null);

            return RecommendationRequestDTO.builder()
                    .id(candidate.getId())
                    .firstName(candidate.getFirstName())
                    .lastName(candidate.getLastName())
                    .city(candidate.getCity())
                    .gender(candidate.getGender())
                    .candidateId(candidate.getCandidateId())
                    .skills(candidate.getSkills())
                    .aboutMe(candidate.getAboutMe())
                    .experience(candidate.getExperience())
                    .education(candidate.getEducation())
                    .cvId(cvId)
                    .file(file)
                    .build();
        })
        .collect(Collectors.toList());

        System.out.println("Recommendation request DTOs: " + recommendationRequestDTOList);
        try {
            // Call the recommendation service to rank candidates
            ResponseDTO responseDTO = candidateRecommendation.rankCandidates(recommendationRequestDTOList, keywords);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            // Handle any exceptions and return an error response
            return ResponseEntity.internalServerError().build();
        }   
 
    }
}
