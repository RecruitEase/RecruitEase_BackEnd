package com.recruitease.auth_service.repository;

import com.recruitease.auth_service.entity.Admin;
import com.recruitease.auth_service.entity.Moderator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeratorRepository extends JpaRepository<Moderator, String> {
}
