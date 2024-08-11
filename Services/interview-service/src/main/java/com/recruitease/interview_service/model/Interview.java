package com.recruitease.interview_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "interviews")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Interview {

    @Id
    private String id;
    private int applicationId;
    private String recruiterID;
    private String candidateID;
    private String type;
    private String date;
    private String time;
    private String location;
    private String link;
    private String dressCode;
    private String description;
    private String cutoffDate;
    private String cutoffTime;

}
