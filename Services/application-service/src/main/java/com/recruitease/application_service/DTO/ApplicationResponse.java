package com.recruitease.application_service.DTO;



import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private String applicationId;
    private String candidateId;
    private String jobId;
    private String cvId;
    private String status;
    private String coverLetter;
    private Integer score;
    private String answers;
    private LocalDateTime createdAt;


}