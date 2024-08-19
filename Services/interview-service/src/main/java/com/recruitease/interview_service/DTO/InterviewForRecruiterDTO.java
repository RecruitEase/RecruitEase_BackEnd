package com.recruitease.interview_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InterviewForRecruiterDTO {
    private String id;
    private String applicationId;
    private String candidateId;
    private String type;
    private String date;
    private String time;
    private String location;
    private String link;
    private String dressCode;
    private String description;
    private String cutoffDate;
    private String cutoffTime;
    private String remainingDays;
}