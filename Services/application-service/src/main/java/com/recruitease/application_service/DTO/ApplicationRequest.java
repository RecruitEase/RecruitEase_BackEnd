package com.recruitease.application_service.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ApplicationRequest(
        String applicationId,
        @NotNull(message = "CandidateId is required")
        @NotEmpty(message = "CandidateId cannot be empty")
        String candidateId,
        @NotNull(message = "JobId is required")
        @NotEmpty(message = "JobId cannot be empty")
        String jobId,
        @NotNull(message = "CvId is required")
        @NotEmpty(message = "CvId cannot be empty")
        String cvId,
        @NotNull(message = "RecruiterId is required")
        @NotEmpty(message = "RecruiterId cannot be empty")
        String recruiterId,
        String status,
        @NotNull(message = "CoverLetter is required")
        @NotEmpty(message = "CoverLetter cannot be empty")
        String coverLetter,
        Integer score,
        String answers
) {
}
