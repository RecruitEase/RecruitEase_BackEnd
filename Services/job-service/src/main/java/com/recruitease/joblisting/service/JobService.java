package com.recruitease.joblisting.service;

import com.recruitease.joblisting.config.CustomUserDetails;
import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.dto.Response;
import com.recruitease.joblisting.model.Job;
import com.recruitease.joblisting.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {

    private final JobRepository jobRepository;
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


    @Transactional(readOnly = true)
    public Response getAllJobs() {
        Response response = new Response();
        try {
         
            List<Job> jobs = jobRepository.findAll();

          
            if (jobs.isEmpty()) {
                log.info("No jobs available");
                response.setCode("204");
                response.setMessage("No jobs available");
                return response;
            }

        
            List<JobResponse> jobResponses = jobs.stream()
                    .map(job -> modelMapper.map(job, JobResponse.class))
                    .toList();

           
            response.setCode("200");
            response.setMessage("Success");
            response.setContent(jobResponses);
            return response;

        } catch (Exception e) {
            log.error("Error occurred while fetching jobs: {}", e.getMessage(), e);
            response.setCode("500");
            response.setMessage("An error occurred while fetching jobs");
            response.setError(e.getMessage());
            return response;
        }
    }

 
    // @Transactional(readOnly = true)
    // public Response getJobById(Long jobId) {
    //     Response response = new Response();
    //     try {
        
    //         Optional<Job> jobOptional = jobRepository.findById(jobId);

        
    //         if (jobOptional.isEmpty()) {
    //             log.warn("Job with ID {} not found", jobId);
    //             response.setCode("404");
    //             response.setMessage("Job not found");
    //             return response;
    //         }

        
    //         JobResponse jobResponse = modelMapper.map(jobOptional.get(), JobResponse.class);

        
    //         response.setCode("200");
    //         response.setMessage("Job found");
    //         response.setContent(jobResponse);
    //         return response;

    //     } catch (Exception e) {
    //         log.error("Error occurred while fetching job with ID {}: {}", jobId, e.getMessage(), e);
    //         response.setCode("500");
    //         response.setMessage("An error occurred while fetching the job");
    //         response.setError(e.getMessage());
    //         return response;
    //     }
    // }
}
