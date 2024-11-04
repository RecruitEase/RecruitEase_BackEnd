package com.recruitease.recommendation.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.recruitease.recommendation.DTO.CandidateRequest;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidaterecommendation")
public class CandidateController {

    @Autowired
    private CandidateRankingService rankingService;

    @PostMapping("/rank")
    public Map<String, Double> rankCandidates(@RequestBody CandidateRequest request) {
        return rankingService.rankCandidatesByKeywords(request.getCandidates(), request.getKeywords());
    }
}


