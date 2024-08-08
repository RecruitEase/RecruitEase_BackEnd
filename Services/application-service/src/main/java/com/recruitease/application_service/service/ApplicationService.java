package com.recruitease.application_service.service;

import com.recruitease.application_service.DTO.ApplicationRequest;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.entity.Application;
import com.recruitease.application_service.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final ApplicationRepository repository;

    public ResponseDTO createApplication(ApplicationRequest request) {

        //mapping
        Application application=modelMapper.map(request,Application.class);


        return null;
    }
}
