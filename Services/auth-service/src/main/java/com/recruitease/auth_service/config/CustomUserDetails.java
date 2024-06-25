package com.recruitease.auth_service.config;

import com.recruitease.auth_service.entity.UserCredential;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {
    private String id;
    private String email;
    private String password;
    private String role;

    public CustomUserDetails(UserCredential userCredential) {
        this.id = userCredential.getId();
        this.email = userCredential.getEmail();
        this.password = userCredential.getPassword();
        this.role = userCredential.getRole();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }







}
