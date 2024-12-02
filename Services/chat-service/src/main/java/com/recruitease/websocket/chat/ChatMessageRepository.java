package com.recruitease.websocket.chat;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

 

List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
