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
                .type(jobRequest.getType())
                .location(jobRequest.getLocation())
                .field(jobRequest.getField())
                .experienceLevel(jobRequest.getExperienceLevel())
                .educationalLevel(jobRequest.getEducationalLevel())
                .description(jobRequest.getDescription())
                .overview(jobRequest.getOverview())
                .deadline(jobRequest.getDeadline())
                .status(jobRequest.getStatus())
                .recruiterId(jobRequest.getRecruiterId())
                .imageUrl(jobRequest.getImageUrl())
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
                   .title(job.getTitle())
                    .type(job.getType())
                    .location(job.getLocation())
                    .field(job.getField())
                    .experienceLevel(job.getExperienceLevel())
                    .educationalLevel(job.getEducationalLevel())
                    .description(job.getDescription())
                    .overview(job.getOverview())
                    .deadline(job.getDeadline())
                    .status(job.getStatus())
                    .recruiterId(job.getRecruiterId())
                    .imageUrl(job.getImageUrl())
                    .build();
    }


}
