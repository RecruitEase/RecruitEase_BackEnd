package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.AuthRequest;
import com.recruitease.auth_service.entity.UserCredential;
import com.recruitease.auth_service.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserCredentialRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(AuthRequest request) {
        System.out.println(modelMapper.map(request,UserCredential.class));

        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        System.out.println(userCredential);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //saving to db
        var user=repository.save(userCredential);
        return user.getId();
    }

    public String generateToken(String userId){
        return jwtService.generateToken(userId);
    }

    public Boolean validateToken(String token,String userID){
        return jwtService.validateToken(token,userID);
    }
}
