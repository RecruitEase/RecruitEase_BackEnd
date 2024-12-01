package com.recruitease.application_service.service;

import com.recruitease.application_service.DTO.ApplicationRequest;
import com.recruitease.application_service.DTO.ApplicationResponse;
import com.recruitease.application_service.DTO.ApplicationUpdateRequestDTO;
import com.recruitease.application_service.DTO.AtsResponse;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.entity.Application;
import com.recruitease.application_service.repository.ApplicationRepository;
import com.recruitease.application_service.repository.HistoryRepository;
import com.recruitease.application_service.util.ApplicationStatusList;
import com.recruitease.application_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final ApplicationRepository repository;
    private final HistoryRepository historyRepository;

    public ResponseDTO createApplication(ApplicationRequest request) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();
        // mapping
        Application application = modelMapper.map(request, Application.class);
        application.setStatus(ApplicationStatusList.Under_Review);
        if (repository.existsByCandidateIdAndJobIdAndStatusNot(application.getCandidateId(), application.getJobId(),
                ApplicationStatusList.Withdrawn)) {
            errors.put("application", "Already exists!");
        }

        if (errors.isEmpty()) {
            // saving to db
            var res = repository.save(application);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Application submitted successfully");
            responseDTO.setContent(res.getApplicationId());

        } else {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;
    }

    public ResponseDTO getApplication(String applicationId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        if (repository.existsById(applicationId)) {
            Application res = repository.findById(applicationId).orElse(null);
            ApplicationResponse response = modelMapper.map(res, ApplicationResponse.class);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(response);
        } else {
            errors.put("application", "Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    public ResponseDTO getApplicationPerCandidate(String candidateId) {

        var responseDTO = new ResponseDTO();
        try {
            // get list of applications for the given candidateId
            var res = repository.findByCandidateId(candidateId)
                    .stream()
                    .map(source -> modelMapper.map(source, ApplicationResponse.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }

        return responseDTO;
    }

    public ResponseDTO getApplicationPerJob(String jobId) {
        var responseDTO = new ResponseDTO();
        try {
            // get list of applications for the given candidateId
            var res = repository.findByJobId(jobId)
                    .stream()
                    .map(source -> modelMapper.map(source, ApplicationResponse.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }

        return responseDTO;
    }

    public ResponseDTO updateApplicationStatus(String applicationId, ApplicationUpdateRequestDTO req) {
        var responseDTO = new ResponseDTO();
        try {
            if (repository.existsById(applicationId)) {
                if (ApplicationStatusList.isStatusNotEqualToAny(req.status())) {
                    // status is invalid
                    responseDTO.setCode(CodeList.RSP_ERROR);
                    responseDTO.setMessage("Invalid status!");
                    responseDTO.setErrors(null);
                } else {
                    repository.updateStatusByApplicationId(req.status(), applicationId);

                    responseDTO.setCode(CodeList.RSP_SUCCESS);
                    responseDTO.setMessage("Success");
                }

            } else {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not found!");
                responseDTO.setErrors(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO updateApplicationStatusBatch(ApplicationUpdateRequestDTO request) {
        var responseDTO = new ResponseDTO();
        try {
            if (request.applicationIds() != null) {
                if (ApplicationStatusList.isStatusNotEqualToAny(request.status())) {
                    // status is invalid
                    responseDTO.setCode(CodeList.RSP_ERROR);
                    responseDTO.setMessage("Invalid status!");
                    responseDTO.setErrors(null);
                } else {

                    repository.updateStatusByApplicationIds(request.status(), request.applicationIds());

                    responseDTO.setCode(CodeList.RSP_SUCCESS);
                    responseDTO.setMessage("Success");
                }
            } else {
                responseDTO.setCode(CodeList.RSP_ERROR);
                responseDTO.setMessage("Invalid Data!");
                responseDTO.setErrors(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO withdrawApplication(String candidateId, String applicationId) {
        var responseDTO = new ResponseDTO();
        try {
            if (repository.existsByCandidateIdAndApplicationId(candidateId, applicationId)) {
                repository.updateStatusByApplicationId(ApplicationStatusList.Withdrawn, applicationId);

                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");

            } else {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Invalid application Id or not belong to the logged user!");
                responseDTO.setErrors(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO getHistory(String candidateId, String recruiterId) {
        var responseDTO = new ResponseDTO();
        try {
            if (repository.existsByCandidateIdAndRecruiterId(candidateId, recruiterId)) {
                var res = repository.findByCandidateIdAndRecruiterId(candidateId, recruiterId)
                        .stream()
                        .map(source -> modelMapper.map(source, ApplicationResponse.class))
                        .toList();

                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(res);
            } else {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not found!");
                responseDTO.setErrors(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }

    public ResponseDTO getHistoryPerApplication(String applicationId) {
        var responseDTO = new ResponseDTO();
        try {
            if (repository.existsById(applicationId)) {
                var res = historyRepository.findByApplicationId(applicationId)
                        .stream()
                        .map(source -> modelMapper.map(source, AtsResponse.class))
                        .toList();

                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                responseDTO.setContent(res);
            } else {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setMessage("Not found!");
                responseDTO.setErrors(null);
            }
        } catch (Exception e) {
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error!");
            responseDTO.setErrors(e.getMessage());
        }

        return responseDTO;
    }
}
