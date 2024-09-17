package com.recruitease.user_detail_service.entity;

import com.recruitease.user_detail_service.entity.UserCredential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

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

    public void updateObject(Recruiter srcObj){

        Optional.ofNullable(srcObj.getFirstName()).ifPresent(this::setFirstName);
        Optional.ofNullable(srcObj.getLastName()).ifPresent(this::setLastName);
        Optional.ofNullable(srcObj.getAddress()).ifPresent(this::setAddress);
        Optional.ofNullable(srcObj.getCity()).ifPresent(this::setCity);
        Optional.ofNullable(srcObj.getMobileNumber()).ifPresent(this::setMobileNumber);
        Optional.ofNullable(srcObj.getProfilePic()).ifPresent(this::setProfilePic);
        Optional.ofNullable(srcObj.getWebsite()).ifPresent(this::setWebsite);
        Optional.ofNullable(srcObj.getBusinessRegistrationNumber()).ifPresent(this::setBusinessRegistrationNumber);
        Optional.ofNullable(srcObj.getCompanyName()).ifPresent(this::setCompanyName);

    }

}
