package com.recruitease.auth_service.config;

import com.recruitease.auth_service.entity.UserCredential;
import com.recruitease.auth_service.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserCredentialRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional< UserCredential > credential=repository.findByEmail(email);
        return credential.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User not found with email: "+email));
    }
}
