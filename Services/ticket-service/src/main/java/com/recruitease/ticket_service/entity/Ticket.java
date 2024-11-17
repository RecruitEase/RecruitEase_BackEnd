package com.recruitease.ticket_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class) //since we need to track created and modified dates
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String ticketId;
    @Column(name = "type", length = 50)
    private TicketType type;
    @Column(name = "status", length = 50)
    private TicketStatus status;
    private String subject;
    private String description;
    private String note; //status change note
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    public enum TicketStatus {
        RESOLVED,
        REJECTED,
        UNDER_REVIEW,
    }

    public enum TicketType {
        JOB_OFFER,
        JOB_APPLICATION,
        JOB_LISTING,
        INTERVIEW,
        PAYMENT,
        OTHER,
        SUPPORT
    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ticket_id", nullable = false)
//    private int ticketId;
//
//    @Column(name = "delay", length = 60, nullable = false)
//    private String delay;
//
//    @Column(name = "subject", length = 60, nullable = false)
//    private String subject;
//
//    @Column(name = "type", length = 60, nullable = false)
//    private String type;
//
//    @Column(name = "job_title", length = 100, nullable = false)
//    private String jobTitle;
//
//    @Column(name = "date", length = 45, nullable = false)
//    private String date;
//
//    @Column(name = "status", length = 60, nullable = false)
//    private String status;
//
//    @Column(name = "name", length = 60, nullable = false)
//    private String name;
//
//    @Column(name = "description", length = 100, nullable = false)
//    private String description;
}
