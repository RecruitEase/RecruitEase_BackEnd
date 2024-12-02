package com.recruitease.recommendation.DTO;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RecommendationResponseDTO {

    private double matchPercentage;
    private String candidateId;
    private String applicationId;
    private String firstName;
    private String lastName;
    private String cvId;
   
}
