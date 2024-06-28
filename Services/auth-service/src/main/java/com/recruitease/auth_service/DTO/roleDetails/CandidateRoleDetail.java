package com.recruitease.auth_service.DTO.roleDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data()
public class CandidateRoleDetail extends RoleDetailObject {
    //attributes other than common attributes
    private String candidateId;
    private String profileStatus;
    private String nic;
    private LocalDate dob;
}

