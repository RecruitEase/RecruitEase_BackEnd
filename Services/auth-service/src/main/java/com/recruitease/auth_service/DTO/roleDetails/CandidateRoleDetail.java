package com.recruitease.auth_service.DTO.roleDetails;

import java.time.LocalDate;

public class CandidateRoleDetail extends RoleDetailObject {
    //attributes other than common attributes
    private String candidateId;
    private String profileStatus;
    private String nic;
    private LocalDate dob;
}
