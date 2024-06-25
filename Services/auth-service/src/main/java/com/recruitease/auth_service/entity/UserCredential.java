package com.recruitease.auth_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_credential")
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String email;
    private String password;
    @Column(columnDefinition = "varchar(255) default 'ROLE_CANDIDATE'")
    private String role;

}
