package com.recruitease.application_service.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final ModelMapper modelMapper;
    private final String id;
    private final String email;
    private final String role;
    private final String isActive;
    private final String createdAt;
    private AdminDetails adminDetails=null;
    private CandidateDetails candidateDetails=null;
    private ModeratorDetails moderatorDetails=null;
    private RecruiterDetails recruiterDetails=null;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String id, String email, String role, String isActive, String createdAt, Object roleDetails, Collection<? extends GrantedAuthority> authorities,ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.id = id;
        this.email = email;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.authorities = authorities;

        if(role.equals("candidate")){
            candidateDetails=modelMapper.map(roleDetails, CandidateDetails.class);
        } else if (role.equals("recruiter")) {
            recruiterDetails=modelMapper.map(roleDetails, RecruiterDetails.class);
        }else if (role.equals("admin")) {
            adminDetails=modelMapper.map(roleDetails, AdminDetails.class);
        }else if (role.equals("moderator")){
            moderatorDetails=modelMapper.map(roleDetails, ModeratorDetails.class);
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return id;
    }
}
