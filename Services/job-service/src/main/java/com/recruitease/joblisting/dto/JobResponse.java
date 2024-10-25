package com.recruitease.joblisting.dto;

import com.recruitease.joblisting.model.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

    private String id;
    private String title;
    private Job.JobType type;
    private Job.Location location;
    private String field;
    private Integer experienceLevel;
    private Integer educationalLevel;
    private String description;
    private String questions;
    private String overview;
    private LocalDate deadline;
    private Job.JobStatus status;
    private String recruiterId;
    private String imageUrl;
    private LocalDateTime createdAt;


}
