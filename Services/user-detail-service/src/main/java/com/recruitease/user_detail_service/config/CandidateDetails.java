package com.recruitease.user_detail_service.config;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CandidateDetails {
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String profilePic;
    private String profileStatus;
    private String candidateId;
}
