package com.recruitease.application_service.repository;

import com.recruitease.application_service.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationRepository extends JpaRepository<Application, String> {
    //to find whether theres a entry with given candidate id and job id
    boolean existsByCandidateIdAndJobId(String candidateId, String jobId);
}
