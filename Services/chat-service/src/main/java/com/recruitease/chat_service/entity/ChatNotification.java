package com.recruitease.chat_service.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatNotification {
    private String id;//will be chatid
    private String senderId;
    private String recipientId;
    private String content;
}
