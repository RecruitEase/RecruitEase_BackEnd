    package com.recruitease.websocket.chat;

import jakarta.persistence.Column;
    import jakarta.persistence.Entity;
    import jakarta.persistence.GeneratedValue;
    import jakarta.persistence.GenerationType;
    import jakarta.persistence.Id;
    import jakarta.persistence.Table;
    import lombok.Data;

    @Data
    @Entity
    @Table(name = "chat_messages")
    public class ChatMessage {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) 
        private Long id;


        @Column(nullable = false)
        private String senderId;

        @Column(nullable = false)
        private String recipientId;

        @Column(nullable = false)
        private String content;

        @Column(nullable = false)
        private String timestamp;
    }
