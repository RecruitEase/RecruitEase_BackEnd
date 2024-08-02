package com.recruitease.chat_service.repository;

import com.recruitease.chat_service.entity.Status;
import com.recruitease.chat_service.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User,String> {
    List<User> findAllByStatus(Status status);
}
