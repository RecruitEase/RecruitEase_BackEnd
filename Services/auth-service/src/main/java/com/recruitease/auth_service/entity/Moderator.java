package com.recruitease.auth_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "moderator")
public class Moderator {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String moderatorId;

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


}
