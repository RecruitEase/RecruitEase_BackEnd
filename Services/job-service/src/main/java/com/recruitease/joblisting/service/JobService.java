package com.recruitease.joblisting.service;

import com.recruitease.joblisting.config.CustomUserDetails;
import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.dto.Response;
import com.recruitease.joblisting.dto.ResponseDTO;
import com.recruitease.joblisting.model.Field;
import com.recruitease.joblisting.model.Job;
import com.recruitease.joblisting.repository.FieldRepository;
import com.recruitease.joblisting.repository.JobRepository;
import com.recruitease.joblisting.util.CodeList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {

    private final JobRepository jobRepository;
    private final FieldRepository fieldRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public Response createJob(JobRequest jobRequest) {
        Response response = new Response();
        try {

            //get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String recruiterId = userDetails.getRecruiterDetails().getRecruiterId();

            Job job = modelMapper.map(jobRequest, Job.class);
            job.setRecruiterId(recruiterId);
            job.setStatus(Job.JobStatus.LIVE);
            Set<Field> jobFields = jobRequest.getFields().stream()
                    .map(key -> fieldRepository.findById(key)
                            .orElseThrow(() -> new IllegalArgumentException("Invalid field key: " + key)))
                    .collect(Collectors.toSet());
            job.setFields(jobFields);
            System.out.println(job.toString());
            var res=jobRepository.save(job);

            response.setCode("201");
            response.setMessage("Job created successfully");
            response.setContent(res.getJobId());
            return response;

        } catch (Exception e) {
            log.error("Error occurred while creating the job: {}", e.getMessage(), e);
            response.setCode("500");
            response.setMessage("Job creation failed");
            response.setError(e.getMessage());
            return response;
        }
    }


    @Transactional
    public ResponseDTO updateJob(Job updateReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            //get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String recruiterId = userDetails.getRecruiterDetails().getRecruiterId();
            System.out.printf("recruiterId: %s", recruiterId);

            Optional<Job> res = jobRepository.findByJobIdAndRecruiterId(updateReq.getJobId(),recruiterId);
            if(res.isPresent()){
                var prevData=res.get();
                prevData.updateObject(updateReq);
                //dont have to call save method, transactional annotation update the db for us if modified
//            Candidate updateResponse=candidateRepository.save(prevData);
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                return responseDTO;
            }else{
                responseDTO.setCode(CodeList.RSP_ERROR);
                responseDTO.setMessage("Not Found!");
                responseDTO.setErrors(errors);
            }


        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }

    public ResponseDTO deleteJob(String jobId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            //get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String recruiterId = userDetails.getRecruiterDetails().getRecruiterId();
            System.out.printf("recruiterId: %s", recruiterId);

            Optional<Job> res = jobRepository.findByJobIdAndRecruiterId(jobId,recruiterId);
            if(res.isPresent()){
                jobRepository.delete(res.get());
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setMessage("Success");
                return responseDTO;
            }else{
                responseDTO.setCode(CodeList.RSP_ERROR);
                responseDTO.setMessage("Not Found!");
                responseDTO.setErrors(errors);
            }


        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }

    public ResponseDTO getAllJobs() {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();

try{
    List<Job> res=jobRepository.findAll();
    responseDTO.setCode(CodeList.RSP_SUCCESS);
    responseDTO.setMessage("Success");
    responseDTO.setContent(res);
} catch (Exception e) {
    responseDTO.setCode(CodeList.RSP_ERROR);
    responseDTO.setMessage("Error Occurred!");
    responseDTO.setErrors(e.getMessage());
}

        return responseDTO;
    }


    public ResponseDTO getJob(String jobId) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


        if(jobRepository.existsById(jobId)){
            Job res=jobRepository.findById(jobId).orElse(null);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }else{
            errors.put("job","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }


        return responseDTO;
    }
}
