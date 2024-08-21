package com.recruitease.ticket_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(schema = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", nullable = false)
    private int ticketId;

    @Column(name = "delay", length = 60, nullable = false)
    private String delay;

    @Column(name = "subject", length = 60, nullable = false)
    private String subject;

    @Column(name = "type", length = 60, nullable = false)
    private String type;

    @Column(name = "job_title", length = 100, nullable = false)
    private String jobTitle;

    @Column(name = "date", length = 45, nullable = false)
    private String date;

    @Column(name = "status", length = 60, nullable = false)
    private String status;

    @Column(name = "name", length = 60, nullable = false)
    private String name;

    @Column(name = "description", length = 100, nullable = false)
    private String description;
}
