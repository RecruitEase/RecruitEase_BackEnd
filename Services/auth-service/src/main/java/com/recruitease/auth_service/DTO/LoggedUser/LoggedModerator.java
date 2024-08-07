package com.recruitease.auth_service.DTO.LoggedUser;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoggedModerator {
    private String id;
    private String email;
    private String role;
    private String isActive;
    private String createdAt;
    private String firstName;
    private String lastName;
    private String profilePic;
    private String moderatorId;
}
