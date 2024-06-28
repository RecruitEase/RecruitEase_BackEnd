package com.recruitease.auth_service.DTO;

import com.recruitease.auth_service.DTO.roleDetails.RoleDetailObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public final class SessionObjectResponse {
    private  String id;
    private  String email;
    private  String role;
    private  Object roleDetails;
    private  String createdAt;
    private  Boolean isActive;
    private  String accessToken;
    private  String refreshToken;


}
;