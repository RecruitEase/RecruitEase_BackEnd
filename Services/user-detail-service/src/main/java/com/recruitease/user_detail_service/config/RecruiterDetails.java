package com.recruitease.user_detail_service.config;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecruiterDetails {
    private String firstName;
    private String lastName;
    private String gender;
    private String profilePic;
    private String recruiterId;
    private String companyName;
    private String city;
    private String businessRegistrationNumber;
    private String website;
}
