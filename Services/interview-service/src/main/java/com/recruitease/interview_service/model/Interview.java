package com.recruitease.interview_service.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

@Document(value = "interviews")
@Builder
@Data
public class Interview {
    @Transient
    public static final String SEQUENCE_NAME = "interview_sequence";

    @Id
    private int interviewId;
    private int applicationId;
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
