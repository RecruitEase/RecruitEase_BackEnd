package com.recruitease.ticket_service.config;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdminDetails {
    private String firstName;
    private String lastName;
    private String city;
    private String gender;
    private String profilePic;
    private String adminId;
}
