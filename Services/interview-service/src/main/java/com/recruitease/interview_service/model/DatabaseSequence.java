package com.recruitease.interview_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Document(collection = "database_sequences")
@Data
public class DatabaseSequence {
    @Id
    private String id;
    private int seq;
}

