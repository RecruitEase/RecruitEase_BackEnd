package com.cv_service.cv_service.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CvDTO {
    private String cvId;
    @NotNull(message = "File can not be null.")
    @NotEmpty(message = "File can not be empty.")
    private String file;
    private  String cvData;
    @NotNull(message = "File can not be null.")
    @NotEmpty(message = "File can not be empty.")
    private  String cvName;
    private  String createdAt;
    private  String candidateId;
}
