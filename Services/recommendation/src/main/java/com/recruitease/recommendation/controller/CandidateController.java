package com.recruitease.recommendation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.recommendation.DTO.ApplicationResponse;
import com.recruitease.recommendation.clients.ApplicationServiceConsumer;
import com.recruitease.recommendation.service.CandidateRecommendation;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/recommendations")
public class CandidateController {

    private final CandidateRecommendation rankingService;
    private final ApplicationServiceConsumer applicationServiceConsumer;

    @Autowired
    public CandidateController(CandidateRecommendation rankingService, ApplicationServiceConsumer applicationServiceConsumer) {
        this.rankingService = rankingService;
        this.applicationServiceConsumer = applicationServiceConsumer;
    }

    // @PostMapping("/rank")
    // public Map<String, Double> rankCandidates(@RequestBody CandidateRequest request) {
    //     return rankingService.rankCandidatesByKeywords(request.getCandidates(), request.getKeywords());
    // }

    HttpServletRequest request;
    String token = request.getHeader("Authorization");

    @GetMapping("/rank")
    public ApplicationResponse rankCandidates() {
        return applicationServiceConsumer.getApplicationResponse(token);
    }
    

}
