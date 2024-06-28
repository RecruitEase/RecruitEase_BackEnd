package com.recruitease.auth_service.repository;

import com.recruitease.auth_service.entity.Candidate;
import com.recruitease.auth_service.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, String> {
}
