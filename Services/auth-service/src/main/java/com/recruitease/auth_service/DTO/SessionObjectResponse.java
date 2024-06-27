package com.recruitease.auth_service.DTO;

import com.recruitease.auth_service.DTO.roleDetails.RoleDetailObject;

public record SessionObjectResponse (
        String id,
        String email,
        String role,
        RoleDetailObject roleDetails,
        String createdAt,
        Boolean isActive,
        String accessToken,
        String refreshToken
){
    //Todo
}
;