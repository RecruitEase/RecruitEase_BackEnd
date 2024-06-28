package com.recruitease.auth_service.DTO.roleDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data()
public abstract class RoleDetailObject {
    // Common attributes and methods for all roles can go here
    private String firstName;
    private String lastName;
    private String address;
    private String mobileNumber;
    private String profilePic;
}
