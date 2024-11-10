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
import com.recruitease.recommendation.DTO.UserDetailsRequestDTO;
import com.recruitease.recommendation.DTO.UserDetailsResponse;
import com.recruitease.recommendation.feign.ApplicationService;
import com.recruitease.recommendation.feign.CvService;
import com.recruitease.recommendation.feign.UserDetailService;

@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    private final ApplicationService applicationService;
    private final CvService cvService;
    private  UserDetailService userDetailService = null ;

    @Autowired
    public CandidateController(ApplicationService applicationService, CvService cvService, UserDetailService userDetailService) {
        this.applicationService = applicationService;
        this.cvService = cvService;
        this.userDetailService = userDetailService;
    }

    @GetMapping("/rank/{jobId}")
    public ResponseEntity<UserDetailsResponse> rankCandidates(@PathVariable("jobId") String jobId) {

        ResponseEntity<ApplicationResponse> applicationResponses = applicationService.getApplicationsPerJob(jobId);

        if (applicationResponses.getBody() == null || applicationResponses.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

        List<ApplicationResponseContent> applications = applicationResponses.getBody().getContent();

        UserDetailsRequestDTO request = new UserDetailsRequestDTO(
                null,
                applications.stream().map(ApplicationResponseContent::getCandidateId).collect(Collectors.toList()),
                null,
                null
        );

        ResponseEntity<UserDetailsResponse> userDetailsResponse = userDetailService.userDetailList(request);

        if (userDetailsResponse.getBody() == null || userDetailsResponse.getBody().getContent() == null) {
            return ResponseEntity.noContent().build();
        }

        return userDetailsResponse;

    
      
        // List<String> cvIds = applications.stream()
        //         .map(ApplicationResponseContent::getCvId)
        //         .collect(Collectors.toList());

       
        // ResponseEntity<CvResponse> cvResponse = cvService.getCvList(cvIds);

        // if (cvResponse.getBody() == null || cvResponse.getBody().getContent() == null) {
        //     return ResponseEntity.noContent().build();
        // }

       
        // return cvResponse;
    }
}
