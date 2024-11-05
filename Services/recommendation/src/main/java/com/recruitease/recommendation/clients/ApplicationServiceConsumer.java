package com.recruitease.recommendation.clients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.recruitease.recommendation.DTO.ApplicationResponse;

@Service
@FeignClient(name = "application-service")
public interface ApplicationServiceConsumer {

    @GetMapping("/api/v1/applications/job/8d0b2df1-5185-4b73-af5d-d6d127f84ad0")
    public ApplicationResponse getApplicationResponse(@RequestHeader("Authorization") String token);

    
}
