package com.recruitease.interview_service.repository;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.model.Interview;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InterviewRepository extends MongoRepository<Interview, Integer> {
    List<Interview> findByRecruiterID(String recruiterId);
    List<Interview> findByCandidateID(String recruiterId);
//    InterviewDTO findByInterviewID(String interviewID);
        Optional<Interview> findById(String id);
}
