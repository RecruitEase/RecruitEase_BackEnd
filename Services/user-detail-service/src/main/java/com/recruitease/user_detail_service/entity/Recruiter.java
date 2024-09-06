package com.recruitease.user_detail_service.entity;

import com.recruitease.user_detail_service.entity.UserCredential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(unique = true)
    private String businessRegistrationNumber;
    private String website;
    @OneToOne
    @JoinColumn(name = "user_id")
    private UserCredential user;
    private String firstName;//contact person name
    private String lastName;
    private String address;
    private String city;
    private String gender;
    @Column(unique = true)
    private String mobileNumber;
    private String profilePic;


}
