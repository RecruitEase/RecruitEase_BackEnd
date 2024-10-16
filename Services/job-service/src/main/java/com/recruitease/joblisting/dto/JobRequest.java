package com.recruitease.joblisting.dto;


import com.recruitease.joblisting.model.Job;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class JobRequest {

    private String title;
    private Job.JobType type;
    private Job.Location location;
    private List<Integer> fields;
    private Integer experienceLevel;
    private Integer educationLevel;
    private String description;
    private String overview;
    private LocalDate deadline;
    private String imageUrl;

}
