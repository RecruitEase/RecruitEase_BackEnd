package com.cv_service.cv_service.config;

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
    private String city;
    private String gender;
    private String companyName;
    private String businessRegistrationNumber;
    private String website;
}
