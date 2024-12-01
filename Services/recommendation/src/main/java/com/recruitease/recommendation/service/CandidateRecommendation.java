package com.recruitease.recommendation.service;

import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.recruitease.recommendation.DTO.RecommendationRequestDTO;
import com.recruitease.recommendation.DTO.RecommendationResponseDTO;
import com.recruitease.recommendation.DTO.ResponseDTO;

import java.io.IOException;

import feign.Response;

@Service
public class CandidateRecommendation {

    private static final Logger logger = LoggerFactory.getLogger(CandidateRecommendation.class);

    private final PdfService pdfService;

    public CandidateRecommendation(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    public ResponseDTO rankCandidates(List<RecommendationRequestDTO> recommendations, List<String> keywords) {
        Map<String, Double> candidateMatchPercentages = new HashMap<>();

        try {
            for (RecommendationRequestDTO dto : recommendations) {
                String combinedContent = extractPersonalData(dto);

                // Fetch and process PDF content if available
                if (dto.getFile() != null && !dto.getFile().isEmpty()) {
                    try {
                        String pdfContent = pdfService.downloadAndExtractText(dto.getFile());
                        combinedContent += " " + pdfContent;
                    } catch (IOException e) {
                        logger.error("Failed to extract text from PDF for candidate ID: {}", dto.getId(), e);
                    }
                }

                // Calculate match percentage with provided keywords
                double matchPercentage = calculateMatchPercentage(combinedContent, keywords);
                candidateMatchPercentages.put(dto.getId(), matchPercentage);
            }

            // Sort candidates by match percentage in descending order
            List<RecommendationResponseDTO> rankedCandidateList = generateRankedCandidates(recommendations, candidateMatchPercentages);

          ResponseDTO response = new ResponseDTO();
            response.setCode("200");
            response.setMessage("Candidates ranked successfully");
            response.setContent(rankedCandidateList);
            return response;

        } catch (Exception e) {
           ResponseDTO response = new ResponseDTO();
            response.setCode("500");
            response.setMessage("Failed to rank candidates");
            return response;
        }
    }

    private String extractPersonalData(RecommendationRequestDTO candidate) {
        StringBuilder content = new StringBuilder();

        Optional.ofNullable(candidate.getGender()).ifPresent(gender -> content.append("Gender: ").append(gender).append(" "));
        Optional.ofNullable(candidate.getSkills()).ifPresent(skills -> content.append("Skills: ").append(skills).append(" "));
        Optional.ofNullable(candidate.getAboutMe()).ifPresent(aboutMe -> content.append("About Me: ").append(aboutMe).append(" "));
        Optional.ofNullable(candidate.getEducation()).ifPresent(education -> content.append("Education: ").append(education).append(" "));
        Optional.ofNullable(candidate.getExperience()).ifPresent(experience -> content.append("Experience: ").append(experience).append(" "));

        return content.toString();
    }

    private double calculateMatchPercentage(String content, List<String> keywords) {
        if (keywords.isEmpty()) {
            logger.warn("No keywords provided for match calculation");
            return 0.0;
        }

        final String lowerCaseContent = content.toLowerCase();
        long matchCount = keywords.stream()
                .filter(keyword -> lowerCaseContent.contains(keyword.toLowerCase()))
                .count();

        return ((double) matchCount / keywords.size()) * 100;
    }

    private List<RecommendationResponseDTO> generateRankedCandidates(
            List<RecommendationRequestDTO> recommendations,
            Map<String, Double> candidateMatchPercentages) {

        return candidateMatchPercentages.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .map(entry -> recommendations.stream()
                        .filter(recommendation -> recommendation.getId().equals(entry.getKey()))
                        .findFirst()
                        .map(recommendation -> RecommendationResponseDTO.builder()
                                .candidateId(recommendation.getCandidateId())
                                .applicationId(recommendation.getApplicationId())
                                .firstName(recommendation.getFirstName())
                                .lastName(recommendation.getLastName())
                                .cvId(recommendation.getCvId())
                                .matchPercentage(entry.getValue())
                                .build())
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
