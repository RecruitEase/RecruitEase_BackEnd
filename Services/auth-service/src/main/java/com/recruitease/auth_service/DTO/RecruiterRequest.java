package com.recruitease.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RecruiterRequest(
        @NotNull(message = "Email is required")
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email is not a valid email address")
        String email,
        @NotNull(message = "Password is required")
        @NotEmpty(message = "Password cannot be empty")
        String password,
        @NotNull(message = "First Name is required")
        @NotEmpty(message = "First Name cannot be empty")
        String firstName,
        @NotNull(message = "Last Name is required")
        @NotEmpty(message = "Last Name cannot be empty")
        String lastName,
        @NotNull(message = "Address is required")
        @NotEmpty(message = "Address cannot be empty")
        String address,
        @NotNull(message = "City is required")
        @NotEmpty(message = "City cannot be empty")
        String city,
        @NotNull(message = "Gender is required")
        @NotEmpty(message = "Gender cannot be empty")
        String gender,
        @NotNull(message = "Mobile Number is required")
        @NotEmpty(message = "Mobile Number cannot be empty")
        String mobileNumber,
        String profilePic,
        @NotNull(message = "Company Name is required")
        @NotEmpty(message = "Company Name cannot be empty")
        String companyName,
        @NotNull(message = "BR number is required")
        @NotEmpty(message = "BR number cannot be empty")
        String businessRegistrationNumber,
        String website
) {
}
