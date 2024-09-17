package com.cv_service.cv_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Cv")
@EntityListeners(AuditingEntityListener.class) //since we need to track created and modified dates
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String cvId;
    private String candidateId;
    private String file;
    private String cvImage;
    private  String cvData;
    private  String cvName;
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
