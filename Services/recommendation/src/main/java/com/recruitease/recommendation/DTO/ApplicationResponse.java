package com.recruitease.recommendation.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationResponse {
    private String code;
    private String message;
    private Object content;
    private Object errors;
}
 
    

