package com.recruitease.application_service.entity;


import com.recruitease.application_service.util.ApplicationStatusList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) //since we need to track created and modified dates
@Table(name = "application")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String applicationId;
    private String candidateId;
    private String jobId;
    private String cvId;
    private String status;
    private String coverLetter;
    private Integer score;
    private String answers;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;


}