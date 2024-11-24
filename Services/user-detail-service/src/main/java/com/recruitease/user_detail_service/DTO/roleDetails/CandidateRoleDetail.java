package com.recruitease.user_detail_service.DTO.roleDetails;

import com.recruitease.user_detail_service.DTO.roleDetails.RoleDetailObject;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data()
public class CandidateRoleDetail extends RoleDetailObject {
    // attributes other than common attributes
    private String candidateId;
    private String profileStatus;
    private String nic;
    private LocalDate dob;
    private String skills;
    private String aboutMe;
    private String experience;
    private String education;
}
