package com.recruitease.recommendation.service;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateRecommendation {

    private final Tika tika = new Tika();

  
    public Map<String, Double> rankCandidatesByKeywords(List<Candidate> candidates, List<String> keywords) {
        Map<String, Double> candidateScores = new HashMap<>();

        for (Candidate candidate : candidates) {
            String combinedText = combineProfileAndCvText(candidate);
            double score = calculateKeywordScore(combinedText, keywords);
            candidateScores.put(candidate.getCandidateId(), score);
        }

        return candidateScores.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private String combineProfileAndCvText(Candidate candidate) {
        StringBuilder combinedText = new StringBuilder();

        combinedText.append(Optional.ofNullable(candidate.getSkills()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getAboutMe()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getExperience()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getEducation()).orElse(""));

      
        if (candidate.getCvFilePath() != null) {
            try {
                File cvFile = new File(candidate.getCvFilePath());
                combinedText.append(" ").append(tika.parseToString(cvFile));
            } catch (IOException | TikaException e) {
                e.printStackTrace();
            }
        }

        return combinedText.toString();
    }

    private double calculateKeywordScore(String text, List<String> keywords) {
        String[] words = text.toLowerCase().split("\\W+");
        long totalWords = words.length;
        long keywordMatches = Arrays.stream(words).filter(keywords::contains).count();
        return (double) keywordMatches / totalWords * 100;
    }
}
