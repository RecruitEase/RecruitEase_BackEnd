package com.recruitease.application_service.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class AtsRequest {
    private String candidateId;
    private String recruiterId;
}
