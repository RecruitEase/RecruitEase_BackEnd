package com.recruitease.interview_service.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EditDTO {

    @NotNull(message = "Type is required")
    @NotEmpty(message = "Type cannot be empty")
    private String type;

    @NotNull(message = "Date is required")
    @NotEmpty(message = "Date cannot be empty")
    private String date;

    @NotNull(message = "Time is required")
    @NotEmpty(message = "Time cannot be empty")
    private String time;

    private String location;
    private String link;
    private String dressCode;
    private String description;

    @NotNull(message = "Cutoff date is required")
    @NotEmpty(message = "Cutoff date cannot be empty")
    private String cutoffDate;

    @NotNull(message = "Cutoff time is required")
    @NotEmpty(message = "Cutoff time cannot be empty")
    private String cutoffTime;
}