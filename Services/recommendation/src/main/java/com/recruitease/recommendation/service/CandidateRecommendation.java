package com.recruitease.recommendation.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

    // import org.apache.tika.Tika;
    // import org.apache.tika.exception.TikaException;

import org.springframework.stereotype.Service;


import com.recruitease.recommendation.DTO.RecommendationResponseDTO;
import com.recruitease.recommendation.DTO.RecommendationRequestDTO;
import com.recruitease.recommendation.DTO.ResponseDTO;

@Service
public class CandidateRecommendation {

    public ResponseDTO rankCandidates (List<RecommendationRequestDTO> recommendations, List<String> keywords) throws Exception {
        Map<String, Double> candidateMatchPercentages = new HashMap<>();

        PdfService pdfService = new PdfService();

        for (RecommendationRequestDTO dto : recommendations) {
            String content = extractPersonalData(dto);

            RecommendationResponseDTO responseDTO = RecommendationResponseDTO.builder()
                    .id(dto.getId())
                    .firstName(dto.getFirstName())
                    .lastName(dto.getLastName())
                    .cvId(dto.getCvId())
                    .build();


            String pdfContent = "";
            if (dto.getFile() != null && !dto.getFile().isEmpty()) {
                pdfContent = pdfService.downloadAndExtractText(dto.getFile());
            }

            String combinedContent = content + " " + pdfContent;

            double matchPercentage = calculateMatchPercentage(combinedContent, keywords);
            responseDTO.setMatchPercentage(matchPercentage);

            candidateMatchPercentages.put(dto.getId(), matchPercentage);



           
        }

        //sort the map by value in descending order
        List<Map.Entry<String, Double>> sortedCandidates = candidateMatchPercentages.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        List<RecommendationResponseDTO> rankedCandidateList = sortedCandidates.stream()
                .map(entry -> {
                    Optional<RecommendationResponseDTO> candidate = recommendations.stream()
                            .filter(recommendation -> recommendation.getId().equals(entry.getKey()))
                            .map(recommendation -> RecommendationResponseDTO.builder()
                                    .id(recommendation.getId())
                                    .firstName(recommendation.getFirstName())
                                    .lastName(recommendation.getLastName())
                                    .cvId(recommendation.getCvId())
                                    .matchPercentage(entry.getValue())
                                    .build())
                            .findFirst();

                    return candidate.orElse(null);
                })
                .collect(Collectors.toList());

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setContent(rankedCandidateList);
        responseDTO.setCode("200");
        responseDTO.setMessage("Success");

        return responseDTO;


    }

    private String extractPersonalData(RecommendationRequestDTO candidate) {
        StringBuilder content = new StringBuilder();

       
        if (candidate.getGender() != null) {
            content.append("Gender: ").append(candidate.getGender()).append(" ");
        }
        if (candidate.getSkills() != null) {
            content.append("Skills: ").append(candidate.getSkills()).append(" ");
        }
        if (candidate.getAboutMe() != null) {
            content.append("About Me: ").append(candidate.getAboutMe()).append(" ");
        }
        if (candidate.getEducation() != null) {
            content.append("Education: ").append(candidate.getEducation()).append(" ");
        }
        if (candidate.getExperience() != null) {
            content.append("Experience: ").append(candidate.getExperience()).append(" ");
        }

        return content.toString();
    }

    private double calculateMatchPercentage(String content, List<String> keywords) {
        int totalKeywords = keywords.size();
        int matchCount = 0;

       
        content = content.toLowerCase();
        for (String keyword : keywords) {
            if (content.contains(keyword.toLowerCase())) {
                matchCount++;
            }
        }


        return totalKeywords == 0 ? 0.0 : ((double) matchCount / totalKeywords) * 100;
    }

    

  
}
