package com.cv_service.cv_service.config;

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
    private String city;
    private String gender;
    private String profilePic;
    private String moderatorId;
}
