package com.recruitease.user_detail_service.DTO.LoggedUser;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoggedRecruiter {
    private String id;
    private String email;
    private String role;
    private Boolean isActive;
    private String createdAt;
    private String firstName;
    private String lastName;
    private String profilePic;
    private String recruiterId;
    private String companyName;
    private String city;
    private String gender;
    private String address;
    private String businessRegistrationNumber;
    private String website;
}
