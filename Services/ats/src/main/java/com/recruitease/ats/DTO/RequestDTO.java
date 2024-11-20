package com.recruitease.ats.DTO;

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
public class RequestDTO{
    private String candidateId;
    private String recruiterId;
}
