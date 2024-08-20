package com.recruitease.auth_service.DTO;

import java.util.List;


//req for user details for the given lists. one or mode lists can be null
public record UserDetailsRequestDTO(
        List<String> recruiterIdList,
        List<String> candidateIdList,
        List<String> moderatorIdList,
        List<String> adminIdList
) {
}
