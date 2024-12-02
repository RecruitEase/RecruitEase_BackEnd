package com.recruitease.websocket.chat;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    /**
     * Save a chat message and send it to the recipient via WebSocket.
     *
     * @param chatMessage The chat message to save.
     * @return The saved chat message.
     */
    public ChatMessage save(ChatMessage chatMessage) {
        // Save the message in the database
        ChatMessage savedMessage = repository.save(chatMessage);

        // Notify the recipient via WebSocket
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipientId(), // User ID of the recipient
                "/queue/messages",           // WebSocket destination for private messages
                new ChatNotification(
                        savedMessage.getId(),
                        savedMessage.getSenderId(),
                        savedMessage.getRecipientId(),
                        savedMessage.getContent()
                )
        );

        return savedMessage;
    }

    /**
     * Retrieve chat messages between two users.
     *
     * @param senderId    The ID of the sender.
     * @param recipientId The ID of the recipient.
     * @return A list of chat messages exchanged between the users.
     */
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
       
        return repository.findBySenderIdAndRecipientId(senderId, recipientId);
    }
}
