package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.AdminModeratorRequest;
import com.recruitease.auth_service.DTO.AuthRequest;
import com.recruitease.auth_service.DTO.CandidateRequest;
import com.recruitease.auth_service.DTO.RecruiterRequest;
import com.recruitease.auth_service.entity.*;
import com.recruitease.auth_service.repository.*;
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
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

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


    @Transactional
    public String registerAdmin(AdminModeratorRequest request) {
        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Admin admin= modelMapper.map(request,Admin.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_ADMIN);


        //saving to db
        var user=repository.save(userCredential);
        admin.setUser(user);
        var adminObj=adminRepository.save(admin);


        return user.getId();
    }

    @Transactional
    public String registerModerator(AdminModeratorRequest request) {
        //mapping
        UserCredential userCredential= modelMapper.map(request,UserCredential.class);
        Moderator moderator= modelMapper.map(request, Moderator.class);
        //encrypting password
        userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        //set role
        userCredential.setRole(RoleList.ROLE_MODERATOR);


        //saving to db
        var user=repository.save(userCredential);
        moderator.setUser(user);
        var moderatorObj=moderatorRepository.save(moderator);


        return user.getId();
    }


}
