package com.recruitease.user_detail_service.repository;

import com.recruitease.user_detail_service.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, String> {
    boolean existsByMobileNumber(String number);
    boolean existsByNic(String number);

    Optional<Candidate> findByUserId(String userId);
}
