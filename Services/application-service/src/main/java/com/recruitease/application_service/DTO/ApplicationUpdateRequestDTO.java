package com.recruitease.application_service.DTO;

import java.util.List;

public record ApplicationUpdateRequestDTO(
        List<String> applicationIds,
        String status
) {
}
