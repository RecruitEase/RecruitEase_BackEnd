package com.recruitease.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recruiter")
public class Recruiter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String recruiterId;
    private String companyName;
    private String businessRegistrationNumber;
    private String website;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserCredential user;
    private String firstName;//contact person name
    private String lastName;
    private String address;
    private String mobileNumber;
    private String profilePic;


}
