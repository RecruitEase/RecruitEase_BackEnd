package com.recruitease.joblisting.repository;


import org.springframework.stereotype.Repository;
import com.recruitease.joblisting.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


@Repository
public interface JobRepository extends JpaRepository<Job, String> {


    Optional<Job> findByJobIdAndRecruiterId(String jobId,String recruiterId);

    List<Job> findAllByRecruiterId(String recruiterId);
    List<Job> findAllByRecruiterIdAndStatus(String recruiterId, Job.JobStatus status);

    List<Job> findAllByStatus(Job.JobStatus jobStatus);

    List<Job> findByStatus(Job.JobStatus jobStatus);
}
