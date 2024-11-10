package com.recruitease.recommendation.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.recruitease.recommendation.DTO.UserDetailsRequestDTO;
import com.recruitease.recommendation.DTO.UserDetailsResponse;

@FeignClient(name = "USER-DETAIL-SERVICE" , path="/user", configuration=FeignConfig.class)
public interface UserDetailService {
    @PostMapping("/detail-list")
    public ResponseEntity<UserDetailsResponse> userDetailList(@RequestBody UserDetailsRequestDTO request);
}
