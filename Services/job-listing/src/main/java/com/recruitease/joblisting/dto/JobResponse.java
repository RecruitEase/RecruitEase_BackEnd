package com.recruitease.joblisting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobResponse {

            private String id;
            private String title;
            private String description;
            private Date deadline;
            private String status;
            private String recruiterId;
            private String location;
            private String industry;
            private String type;
            private String level;
}
