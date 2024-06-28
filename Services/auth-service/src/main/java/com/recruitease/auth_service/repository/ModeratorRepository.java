package com.recruitease.auth_service.repository;

import com.recruitease.auth_service.entity.Admin;
import com.recruitease.auth_service.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, String> {
    boolean existsByMobileNumber(String mobileNumber);

    Optional<Moderator> findByUserId(String userId);
}
