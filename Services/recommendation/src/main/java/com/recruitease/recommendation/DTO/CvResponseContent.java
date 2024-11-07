package com.recruitease.recommendation.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CvResponseContent {
    private String cvId;

    private String file;

    private String cvImage;
    private  String cvData;

    private  String cvName;
    private  String createdAt;
    private  String candidateId;
    private  Boolean isDeleted;
}
