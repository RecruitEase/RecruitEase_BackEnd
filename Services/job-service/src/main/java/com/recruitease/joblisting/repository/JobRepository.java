package com.recruitease.joblisting.repository;


import org.springframework.stereotype.Repository;
import com.recruitease.joblisting.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface JobRepository extends JpaRepository<Job, String> {




}
