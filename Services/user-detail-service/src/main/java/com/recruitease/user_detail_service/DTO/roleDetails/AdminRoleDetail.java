package com.recruitease.user_detail_service.DTO.roleDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data()
public class AdminRoleDetail extends RoleDetailObject {
    //attributes other than common attributes
    private String adminId;

}

