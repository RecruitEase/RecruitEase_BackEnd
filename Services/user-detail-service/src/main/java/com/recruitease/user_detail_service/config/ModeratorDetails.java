package com.recruitease.user_detail_service.config;

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
