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
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String adminId;

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

    public void updateObject(Admin srcObj){

        Optional.ofNullable(srcObj.getFirstName()).ifPresent(this::setFirstName);
        Optional.ofNullable(srcObj.getLastName()).ifPresent(this::setLastName);
        Optional.ofNullable(srcObj.getAddress()).ifPresent(this::setAddress);
        Optional.ofNullable(srcObj.getCity()).ifPresent(this::setCity);
        Optional.ofNullable(srcObj.getMobileNumber()).ifPresent(this::setMobileNumber);
        Optional.ofNullable(srcObj.getProfilePic()).ifPresent(this::setProfilePic);

    }
}
