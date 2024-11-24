package com.recruitease.recommendation.DTO;

import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class RecommendationRequestDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String candidateId;
    private String skills;
    private String aboutMe;
    private String experience;
    private String education;
    private String cvId;
    private String file;
}
