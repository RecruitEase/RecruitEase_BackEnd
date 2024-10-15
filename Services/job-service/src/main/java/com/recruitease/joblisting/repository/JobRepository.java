package com.recruitease.joblisting.repository;


import org.springframework.stereotype.Repository;
import com.recruitease.joblisting.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


@Repository
public interface JobRepository extends JpaRepository<Job, String> {


    Optional<Job> findByJobIdAndRecruiterId(String jobId,String recruiterId);
}
