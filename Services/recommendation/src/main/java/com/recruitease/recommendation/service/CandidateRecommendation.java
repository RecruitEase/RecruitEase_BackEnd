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

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;

import com.recruitease.recommendation.DTO.CvResponseContent;
import com.recruitease.recommendation.DTO.UserDetailsResponseContent;

@Service
public class CandidateRecommendation {

    private final Tika tika = new Tika();

    public Map<String, Double> rankCandidatesByKeywords(
            List<UserDetailsResponseContent> candidateDetails,
            List<CvResponseContent> cvDetails,
            List<String> keywords
    ) {
        Map<String, Double> candidateScores = new HashMap<>();

        for (UserDetailsResponseContent candidate : candidateDetails) {
            String combinedText = combineProfileAndCvText(candidate, cvDetails);
            double score = calculateKeywordScore(combinedText, keywords);
            candidateScores.put(candidate.getCandidateId(), score);
        }

        return candidateScores.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private String combineProfileAndCvText(UserDetailsResponseContent candidate, List<CvResponseContent> cvDetails) {
        StringBuilder combinedText = new StringBuilder();

        combinedText.append(Optional.ofNullable(candidate.getSkills()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getAboutMe()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getExperience()).orElse(""))
                    .append(" ")
                    .append(Optional.ofNullable(candidate.getEducation()).orElse(""));

        cvDetails.stream()
                .filter(cv -> cv.getCandidateId().equals(candidate.getCandidateId()) && !cv.getIsDeleted())
                .findFirst()
                .ifPresent(cv -> {
                    try {
                        String cvText = downloadAndExtractText(cv.getFile());
                        combinedText.append(" ").append(cvText);
                    } catch (IOException | TikaException e) {
                        e.printStackTrace();
                    }
                });

        return combinedText.toString();
    }

    private String downloadAndExtractText(String fileUrl) throws IOException, TikaException {
        File tempFile = File.createTempFile("cv_", ".pdf");
        try (InputStream in = new URL(fileUrl).openStream();
             FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return tika.parseToString(tempFile);
        } finally {
            tempFile.delete();
        }
    }

    private double calculateKeywordScore(String text, List<String> keywords) {
        String[] words = text.toLowerCase().split("\\W+");
        long totalWords = words.length;
        long keywordMatches = Arrays.stream(words).filter(keywords::contains).count();
        return (double) keywordMatches / totalWords * 100;
    }
}
