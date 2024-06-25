package com.recruitease.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        String id,
        @NotNull(message = "Email is required")
        @Email(message = "Email is not a valid email address")
        String email,
        @NotNull(message = "Password is required")
        String password,
        String role
) {
}
