package com.recruitease.joblisting.service;

import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.model.Job;
import com.recruitease.joblisting.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class JobService {
    private final JobRepository jobRepository;

    public void createJob(JobRequest jobRequest) {
        Job job = Job.builder()
                .title(jobRequest.getTitle())
                .description(jobRequest.getDescription())
                .deadline(jobRequest.getDeadline())
                .status(jobRequest.getStatus())
                .recruiterId(jobRequest.getRecruiterId())
                .location(jobRequest.getLocation())
                .industry(jobRequest.getIndustry())
                .type(jobRequest.getType())
                .level(jobRequest.getLevel())
                .build();

        jobRepository.save(job);
        log.info("Job {} created", job.getId());

    }

    public List<JobResponse> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();

        return jobs.stream().map(this::mapToJobResponse).toList();
    }

    private JobResponse mapToJobResponse(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .deadline(job.getDeadline())
                .status(job.getStatus())
                .recruiterId(job.getRecruiterId())
                .location(job.getLocation())
                .industry(job.getIndustry())
                .type(job.getType())
                .level(job.getLevel())
                .build();
    }


}