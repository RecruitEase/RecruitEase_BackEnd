package com.recruitease.joblisting.repository;

import com.recruitease.joblisting.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobRepository extends MongoRepository<Job, String> {
}
