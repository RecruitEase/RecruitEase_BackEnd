package com.recruitease.recommendation.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    private static final Logger logger = LoggerFactory.getLogger(CandidateController.class);

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
    public ResponseEntity<ResponseDTO> rankCandidates(
            @PathVariable("jobId") String jobId, 
            @RequestParam List<String> keywords) {
        try {
            // Fetch applications for the specified job
            ResponseEntity<ApplicationResponse> applicationResponse = applicationService.getApplicationsPerJob(jobId);

            if (!applicationResponse.hasBody() || applicationResponse.getBody().getContent().isEmpty()) {
                ResponseDTO responseDTO = new ResponseDTO();
                responseDTO.setCode("404");
                responseDTO.setMessage("No applications found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            
            
            }

            // Extract candidate and CV IDs from applications
            List<ApplicationResponseContent> applications = applicationResponse.getBody().getContent();
            List<String> candidateIds = applications.stream()
                    .map(ApplicationResponseContent::getCandidateId)
                    .collect(Collectors.toList());

            List<String> cvIds = applications.stream()
                    .map(ApplicationResponseContent::getCvId)
                    .collect(Collectors.toList());

            // Fetch candidate details from UserDetailService
            UserDetailsRequestDTO userDetailsRequest = new UserDetailsRequestDTO(null, candidateIds, null, null);
            ResponseEntity<UserDetailsResponse> userDetailsResponse = userDetailService.userDetailList(userDetailsRequest);

            if (!userDetailsResponse.hasBody() || userDetailsResponse.getBody().getContent().getCandidateList().isEmpty()) {
               ResponseDTO responseDTO = new ResponseDTO();
                responseDTO.setCode("404");
                responseDTO.setMessage("No candidate details found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }

            List<CandidateData> candidateList = userDetailsResponse.getBody().getContent().getCandidateList();

            // Fetch CV details from CvService
            ResponseEntity<CvResponse> cvResponse = cvService.getCvList(cvIds);

            if (!cvResponse.hasBody() || cvResponse.getBody().getContent().isEmpty()) {
                ResponseDTO responseDTO = new ResponseDTO();
                responseDTO.setCode("404");
                responseDTO.setMessage("No CV details found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDTO);
            }

            List<CvResponseContent> cvDetails = cvResponse.getBody().getContent();

            // Prepare RecommendationRequestDTOs
            List<RecommendationRequestDTO> recommendationRequestDTOList = candidateList.stream()
                    .map(candidate -> {
                        String cvId = findCvId(cvDetails, candidate.getCandidateId());
                        String file = findFile(cvDetails, candidate.getCandidateId());
                        String applicationId = findApplicationId(applications, candidate.getCandidateId());
                        

                        return RecommendationRequestDTO.builder()
                                .id(candidate.getId())
                                .firstName(candidate.getFirstName())
                                .applicationId(applicationId)
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

            // Rank candidates
            ResponseDTO responseDTO = candidateRecommendation.rankCandidates(recommendationRequestDTOList, keywords);
            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            logger.error("Error occurred while fetching candidate recommendations", e);
            ResponseDTO responseDTO = new ResponseDTO();
            responseDTO.setCode("500");
            responseDTO.setMessage("Internal server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDTO);   
        }
    }

    private String findCvId(List<CvResponseContent> cvDetails, String candidateId) {
        return cvDetails.stream()
                .filter(cv -> cv.getCandidateId().equals(candidateId))
                .map(CvResponseContent::getCvId)
                .findFirst()
                .orElse(null);
    }

    private String findFile(List<CvResponseContent> cvDetails, String candidateId) {
        return cvDetails.stream()
                .filter(cv -> cv.getCandidateId().equals(candidateId))
                .map(CvResponseContent::getFile)
                .findFirst()
                .orElse(null);
    }

    private String findApplicationId(List<ApplicationResponseContent> applications, String candidateId) {
        return applications.stream()
                .filter(application -> application.getCandidateId().equals(candidateId))
                .map(ApplicationResponseContent::getApplicationId)
                .findFirst()
                .orElse(null);
    }   
}
