package com.recruitease.payment_service.config;

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
    private String gender;
    private String city;
    private String profilePic;
    private String moderatorId;
}
