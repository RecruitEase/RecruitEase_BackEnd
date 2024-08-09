package com.recruitease.application_service.service;

import com.recruitease.application_service.DTO.ApplicationRequest;
import com.recruitease.application_service.DTO.ApplicationResponse;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.entity.Application;
import com.recruitease.application_service.repository.ApplicationRepository;
import com.recruitease.application_service.util.ApplicationStatusList;
import com.recruitease.application_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final ApplicationRepository repository;

    public ResponseDTO createApplication(ApplicationRequest request) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();
        //mapping
        Application application=modelMapper.map(request,Application.class);
        application.setStatus(ApplicationStatusList.Under_Review);
        if(repository.existsByCandidateIdAndJobId(application.getCandidateId(),application.getJobId())){
            errors.put("application","Already exists!");
        }


        if(errors.isEmpty()){
            //saving to db
            var res=repository.save(application);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Application submitted successfully");
            responseDTO.setContent(res.getApplicationId());

        }else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;
    }

    public ResponseDTO getApplication(String applicationId) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


        if(repository.existsById(applicationId)){
            Application res=repository.findById(applicationId).orElse(null);
            ApplicationResponse response=modelMapper.map(res,ApplicationResponse.class);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(response);
        }else{
            errors.put("application","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }
}
