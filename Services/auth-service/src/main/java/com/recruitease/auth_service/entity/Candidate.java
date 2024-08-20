package com.recruitease.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "candidate")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String candidateId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserCredential user;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String gender;
    @Column(unique = true)
    private String mobileNumber;
    private String profilePic;
    private String profileStatus="Looking for Jobs";
    @Column(unique = true)
    private String nic;
    private LocalDate dob;

}
