package com.recruitease.application_service.config;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModeratorDetails {
    private String firstName;
    private String lastName;
    private String profilePic;
    private String moderatorId;
}
