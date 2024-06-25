package com.recruitease.auth_service.repository;

import com.recruitease.auth_service.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
}
