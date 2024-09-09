package com.recruitease.user_detail_service.DTO.LoggedUser;

public record LoggedUserHeader(
        String id,
        String email,
        String role,
        Object roleDetails,
        Boolean isActive,
        String createdAt,
        String sub,
        String iat,
        String exp
) {
}
