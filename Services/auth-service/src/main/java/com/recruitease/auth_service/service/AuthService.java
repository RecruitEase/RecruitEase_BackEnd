package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.AuthRequest;
import com.recruitease.auth_service.DTO.CandidateRequest;
import com.recruitease.auth_service.DTO.RecruiterRequest;
import com.recruitease.auth_service.entity.Candidate;
import com.recruitease.auth_service.entity.Recruiter;
import com.recruitease.auth_service.entity.UserCredential;
import com.recruitease.auth_service.repository.CandidateRepository;
import com.recruitease.auth_service.repository.RecruiterRepository;
import com.recruitease.auth_service.repository.UserCredentialRepository;
import com.recruitease.auth_service.util.RoleList;
import jakarta.transaction.Transactional;
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
    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String saveUser(AuthRequest request) {

        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //saving to db
        var user=repository.save(userCredential);
        return user.getId();
    }

    public String generateToken(String userId){
        return jwtService.generateToken(userId);
    }

    public Boolean validateToken(String token){
        System.out.println(token);
        return jwtService.validateToken(token);
    }

    @Transactional //to make it save both as one transaction
    public String registerCandidate(CandidateRequest request) {
        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Candidate candidate= modelMapper.map(request,Candidate.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_CANDIDATE);

        //saving to db
        var user=repository.save(userCredential);
        candidate.setUser(user);
        var candidateObj=candidateRepository.save(candidate);


        return user.getId();
    }

    @Transactional //to make it save both as one transaction
    public String registerRecruiter(RecruiterRequest request) {
        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Recruiter recruiter= modelMapper.map(request,Recruiter.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_RECRUITER);


        //saving to db
        var user=repository.save(userCredential);
        recruiter.setUser(user);
        var recruiterObj=recruiterRepository.save(recruiter);


        return user.getId();
    }
}
