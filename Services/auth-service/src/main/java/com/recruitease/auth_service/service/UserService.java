package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.LoggedUser.LoggedCandidate;
import com.recruitease.auth_service.DTO.ResponseDTO;
import com.recruitease.auth_service.DTO.UserDetailsRequestDTO;
import com.recruitease.auth_service.DTO.roleDetails.CandidateRoleDetail;
import com.recruitease.auth_service.entity.Candidate;
import com.recruitease.auth_service.repository.*;
import com.recruitease.auth_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    public ResponseDTO getUserDetailsLists(UserDetailsRequestDTO request) {
        return null;
    }

    public ResponseDTO getUserDetail(String id) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();


            Candidate res=candidateRepository.findById(id).orElse(null);
        LoggedCandidate cand=modelMapper.map(res,LoggedCandidate.class);

            cand.setId(res.getUser().getId());
            cand.setEmail(res.getUser().getEmail());
            cand.setRole("candidate");
            cand.setIsActive(res.getUser().getIsActive());
            cand.setCreatedAt(res.getUser().getCreatedAt().toString());


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(cand);


        return responseDTO;
    }
}
