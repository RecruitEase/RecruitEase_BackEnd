package com.recruitease.ticket_service.repository;

import com.recruitease.ticket_service.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
    List<Ticket> findByCreatorRoleAndCreatorId(Ticket.CreatorRole role,String creatorId);
}
