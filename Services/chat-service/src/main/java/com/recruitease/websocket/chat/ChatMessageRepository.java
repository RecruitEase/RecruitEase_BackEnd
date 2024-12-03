package com.recruitease.websocket.chat;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

 

List<ChatMessage> findBySenderIdAndRecipientId(String senderId, String recipientId);

@Query("SELECT DISTINCT cm.senderId FROM ChatMessage cm WHERE cm.recipientId = :userId")
List<String> findChatsByRecipientId(@Param("userId") String userId);

}
