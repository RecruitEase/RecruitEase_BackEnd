package com.recruitease.auth_service.DTO.roleDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data()
public class ModeratorRoleDetail extends RoleDetailObject {
    //attributes other than common attributes
    private String moderatorId;

}

