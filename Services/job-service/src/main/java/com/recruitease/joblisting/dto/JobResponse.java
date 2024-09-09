package com.recruitease.joblisting.dto;

import com.recruitease.joblisting.model.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private Job.ExperienceLevel experienceLevel;
    private Job.EducationLevel educationalLevel;
    private String description;
    private String overview;
    private LocalDate deadline;
    private Job.JobStatus status;
    private String recruiterId;
    private String imageUrl;

}
