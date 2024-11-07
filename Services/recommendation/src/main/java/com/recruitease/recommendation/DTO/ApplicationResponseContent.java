package com.recruitease.recommendation.DTO;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponseContent {
    private String applicationId;
    private String candidateId;
    private String jobId;
    private String cvId;
    private String recruiterId;
    private String status;
    private String coverLetter;
    private Integer score;
    private String answers;
    private LocalDateTime createdAt;


}