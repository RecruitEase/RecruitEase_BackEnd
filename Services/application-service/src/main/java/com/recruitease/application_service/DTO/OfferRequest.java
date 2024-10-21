package com.recruitease.application_service.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OfferRequest(
        String offerId,
        @NotNull(message = "ApplicationId is required")
        @NotEmpty(message = "ApplicationId cannot be empty")
        String applicationId,
        @NotNull(message = "CandidateId is required")
        @NotEmpty(message = "CandidateId cannot be empty")
        String candidateId,
        @NotNull(message = "JobId is required")
        @NotEmpty(message = "JobId cannot be empty")
        String jobId,
        @NotNull(message = "RecruiterId is required")
        @NotEmpty(message = "RecruiterId cannot be empty")
        String recruiterId,
        @NotNull(message = "CuttOff Date and Time is required")
        LocalDateTime finalAcceptanceDateTime,
        @NotNull(message = "Start Date and Time is required")
        LocalDateTime startDateTime,
        @NotNull(message = "Description is required")
        @NotEmpty(message = "Description cannot be empty")
        String description,
        @NotNull(message = "Location is required")
        @NotEmpty(message = "Location cannot be empty")
        String location
) {

}
