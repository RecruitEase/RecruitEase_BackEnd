package com.recruitease.recommendation.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.recruitease.recommendation.DTO.CvResponse;


@FeignClient(name = "CV-SERVICE" , path="/api/v1/cv", configuration=FeignConfig.class)
public interface CvService{

    @PostMapping("/list")
    public ResponseEntity<CvResponse> getCvList(@RequestBody List<String> cvIds);
    
    
}
