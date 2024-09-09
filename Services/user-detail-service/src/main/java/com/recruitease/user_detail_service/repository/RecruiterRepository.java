package com.recruitease.user_detail_service.repository;

import com.recruitease.user_detail_service.entity.Recruiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, String> {
    boolean existsByBusinessRegistrationNumber(String businessRegistrationNumber);

    boolean existsByMobileNumber(String mobileNumber);

    Optional<Recruiter> findByUserId(String userId);
}
