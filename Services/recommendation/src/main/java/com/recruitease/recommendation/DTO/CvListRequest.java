package com.recruitease.recommendation.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CvListRequest {
    private List<String> cvIds;
}
 
    

