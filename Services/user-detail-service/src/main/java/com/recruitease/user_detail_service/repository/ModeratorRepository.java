package com.recruitease.user_detail_service.repository;

import com.recruitease.user_detail_service.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, String> {
    boolean existsByMobileNumber(String mobileNumber);

    Optional<Moderator> findByUserId(String userId);
}
