package com.recruitease.user_detail_service.entity;

import com.recruitease.user_detail_service.entity.UserCredential;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate //for dynamic sql for only needed fields
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
    @Column(columnDefinition="TEXT")
    private String skills;
    @Column(columnDefinition="TEXT")
    private String experience;
    @Column(columnDefinition="TEXT")
    private String education;


    public void updateObject(Candidate srcObj){
//        if(srcObj.getFirstName()!=null)setFirstName(srcObj.getFirstName());
//        if(srcObj.getLastName()!=null)setLastName(srcObj.getLastName());
//        if(srcObj.getAddress()!=null)setAddress(srcObj.getAddress());
//        if(srcObj.getCity()!=null)setCity(srcObj.getCity());
//        if(srcObj.getMobileNumber()!=null)setMobileNumber(srcObj.getMobileNumber());
//        if(srcObj.getProfilePic()!=null)setProfilePic(srcObj.getProfilePic());
//        if(srcObj.getProfileStatus()!=null)setProfileStatus(srcObj.getProfileStatus());

        Optional.ofNullable(srcObj.getFirstName()).ifPresent(this::setFirstName);
        Optional.ofNullable(srcObj.getLastName()).ifPresent(this::setLastName);
        Optional.ofNullable(srcObj.getAddress()).ifPresent(this::setAddress);
        Optional.ofNullable(srcObj.getCity()).ifPresent(this::setCity);
        Optional.ofNullable(srcObj.getMobileNumber()).ifPresent(this::setMobileNumber);
        Optional.ofNullable(srcObj.getProfilePic()).ifPresent(this::setProfilePic);
        Optional.ofNullable(srcObj.getProfileStatus()).ifPresent(this::setProfileStatus);
        Optional.ofNullable(srcObj.getSkills()).ifPresent(this::setSkills);
        Optional.ofNullable(srcObj.getExperience()).ifPresent(this::setExperience);
        Optional.ofNullable(srcObj.getEducation()).ifPresent(this::setEducation);

    }
}
