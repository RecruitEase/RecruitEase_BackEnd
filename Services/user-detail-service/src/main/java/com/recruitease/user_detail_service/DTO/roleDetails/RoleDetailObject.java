package com.recruitease.user_detail_service.DTO.roleDetails;

import lombok.Data;

@Data()
public abstract class RoleDetailObject {
    // Common attributes and methods for all roles can go here
    private String firstName;
    private String lastName;
    private String address;
    private String gender;
    private String city;
    private String mobileNumber;
    private String profilePic;
}
