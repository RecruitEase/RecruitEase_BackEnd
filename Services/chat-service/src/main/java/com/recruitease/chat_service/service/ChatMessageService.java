package com.recruitease.chat_service.service;

import com.recruitease.chat_service.entity.ChatMessage;
import com.recruitease.chat_service.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    //saving message
    public ChatMessage save(ChatMessage chatMessage) {
        var chatId=chatRoomService.getChatRoomId(
                chatMessage.getSenderId(),
                chatMessage.getRecipientId(),
                true
        ).orElseThrow();// Todo throw exception

        chatMessage.setChatId(chatId);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    //finding msg by recpienf and sender id
    public List<ChatMessage> findChatMessages(
            String senderId,
            String recipientId
    ){
        var chatId=chatRoomService.getChatRoomId(
                senderId,
                recipientId,
                false);

        return chatId.map(chatMessageRepository::findByChatId)
                .orElse(new ArrayList<>());
    }
}
