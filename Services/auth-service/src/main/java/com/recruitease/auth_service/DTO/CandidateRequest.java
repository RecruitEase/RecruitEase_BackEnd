package com.recruitease.auth_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;

public record CandidateRequest(
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
        String profileStatus, //"Currently Working","Looking for Jobs","On a Career Break"
        @NotNull(message = "NIC is required")
        @NotEmpty(message = "NIC cannot be empty")
        String nic,
        @NotNull(message = "Date of Birth is required")
        LocalDate dob
) {
}
