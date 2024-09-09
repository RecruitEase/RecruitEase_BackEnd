package com.recruitease.joblisting.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Response {
    private String code;
    private String message;
    private Object content;
    private Object error;

    
    
}
