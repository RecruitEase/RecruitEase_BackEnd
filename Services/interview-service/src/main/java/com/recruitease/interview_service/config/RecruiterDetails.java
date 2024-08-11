package com.recruitease.interview_service.config;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecruiterDetails {
    private String firstName;
    private String lastName;
    private String profilePic;
    private String recruiterId;
    private String companyName;
    private String businessRegistrationNumber;
    private String website;
}
