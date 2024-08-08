package com.recruitease.interview_service.repository;

import com.recruitease.interview_service.model.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InterviewRepository extends MongoRepository<Interview, Integer> {
}
