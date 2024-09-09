package com.recruitease.user_detail_service.DTO.LoggedUser;
import lombok.*;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoggedCandidate {
    private String id;
    private String email;
    private String role;
    private Boolean isActive;
    private String createdAt;
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String profilePic;
    private String profileStatus;
    private String candidateId;
}