package com.recruitease.user_detail_service.DTO.roleDetails;

import com.recruitease.user_detail_service.DTO.roleDetails.RoleDetailObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data()
public class RecruiterRoleDetail extends RoleDetailObject {
    //attributes other than common attributes
    private String recruiterId;
    private String companyName;
    private String businessRegistrationNumber;
    private String website;
}

