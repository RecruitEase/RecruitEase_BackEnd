package com.recruitease.application_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String offerId;

    @ManyToOne()
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    private String candidateId;
    private String recruiterId;
    private String jobId;

    private String location;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    private LocalDateTime startDateTime;
    private LocalDateTime finalAcceptanceDateTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT",nullable = true)
    private String statusChangeNote;


    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    public enum OfferStatus {
        PENDING,
        ACCEPTED,
        REJECTED,
        EXPIRED,
        CANCELED//canceled by recruiter
    }
}