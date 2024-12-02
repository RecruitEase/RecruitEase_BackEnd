package com.recruitease.application_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AtsResponse {

    private String applicationId;
    private String status;
    private LocalDateTime updatedAt;

}
