package com.recruitease.interview_service.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record InterviewDTO (
        @NotNull(message = "Application ID is required")
        @NotEmpty(message = "Application ID cannot be empty")
     String id,
    @NotNull(message = "Application ID is required")
    @NotEmpty(message = "Application ID cannot be empty")
     String applicationId,


    @NotNull(message = "Candidate ID is required")
    @NotEmpty(message = "Candidate ID cannot be empty")
     String candidateID,

    @NotNull(message = "Type is required")
    @NotEmpty(message = "Type cannot be empty")
     String type,

    @NotNull(message = "Date is required")
    @NotEmpty(message = "Date cannot be empty")
     String date,

    @NotNull(message = "Time is required")
    @NotEmpty(message = "Time cannot be empty")
     String time,

     String location,
     String link,
     String dressCode,
     String description,

    @NotNull(message = "Cutoff date is required")
    @NotEmpty(message = "Cutoff date cannot be empty")
     String cutoffDate,

    @NotNull(message = "Cutoff time is required")
    @NotEmpty(message = "Cutoff time cannot be empty")
     String cutoffTime

){}
