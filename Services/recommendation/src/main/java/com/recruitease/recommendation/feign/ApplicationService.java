package com.recruitease.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.recruitease.recommendation.DTO.ApplicationResponse;


@FeignClient(name = "APPLICATION-SERVICE" , path="/api/v1/applications", configuration=FeignConfig.class)
public interface ApplicationService {

    @GetMapping("/job/{jobId}")
    public ResponseEntity<ApplicationResponse> getApplicationsPerJob(@PathVariable("jobId") String jobId);
    
}
