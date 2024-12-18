package com.recruitease.recommendation.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailsResponse {
    private String code;
    private String message;
    private UserDetailsResponseContent content;
    private Object errors;
}
