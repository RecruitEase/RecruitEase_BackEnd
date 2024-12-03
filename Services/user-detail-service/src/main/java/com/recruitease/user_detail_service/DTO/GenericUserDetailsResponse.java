package com.recruitease.user_detail_service.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericUserDetailsResponse {

    private String role;
    private String userId;
    private String email;
    private String name;
    private String roleId;

}
