package com.recruitease.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;

public record AuthRequest(
        @NotNull(message = "Email is required")
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email is not a valid email address")
        String email,
        @NotNull(message = "Password is required")
        @NotEmpty(message = "Password cannot be empty")
        String password
) {
}
