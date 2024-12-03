package com.recruitease.user_detail_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.recruitease.user_detail_service.entity.UserCredential;

@Repository
public interface UserRepository extends JpaRepository<UserCredential, String> {

}
