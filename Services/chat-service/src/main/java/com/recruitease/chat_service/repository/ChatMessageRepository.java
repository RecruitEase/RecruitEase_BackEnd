package com.recruitease.chat_service.repository;

import com.recruitease.chat_service.entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatMessageRepository  extends MongoRepository<ChatMessage,String> {
}
