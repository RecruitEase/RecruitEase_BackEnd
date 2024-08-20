package com.recruitease.auth_service.service;

import com.recruitease.auth_service.DTO.LoggedUser.LoggedAdmin;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedCandidate;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedModerator;
import com.recruitease.auth_service.DTO.LoggedUser.LoggedRecruiter;
import com.recruitease.auth_service.DTO.ResponseDTO;
import com.recruitease.auth_service.DTO.UserDetailsRequestDTO;
import com.recruitease.auth_service.DTO.UserDetailsResponseDTO;
import com.recruitease.auth_service.DTO.roleDetails.CandidateRoleDetail;
import com.recruitease.auth_service.entity.Admin;
import com.recruitease.auth_service.entity.Candidate;
import com.recruitease.auth_service.entity.Moderator;
import com.recruitease.auth_service.entity.Recruiter;
import com.recruitease.auth_service.repository.*;
import com.recruitease.auth_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    public ResponseDTO getUserDetailsLists(UserDetailsRequestDTO request) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();
        var userDetailResponse=UserDetailsResponseDTO.builder()
                .recruiterIdList(request.recruiterIdList())
                .candidateIdList(request.candidateIdList())
                .moderatorIdList(request.moderatorIdList())
                .adminIdList(request.adminIdList())
                .build();


        try{
            if(request.adminIdList()!=null){
                List<Admin> adminList=adminRepository.findAllById(request.adminIdList());
                List<LoggedAdmin> adminDetailList=adminList.stream().map(this::mapToLoggedAdmin).toList();
                userDetailResponse.setAdminList(adminDetailList);
            }
            if(request.moderatorIdList()!=null){
                List<Moderator> moderatorList=moderatorRepository.findAllById(request.moderatorIdList());
                List<LoggedModerator> moderatorDetailList=moderatorList.stream().map(this::mapToLoggedModerator).toList();
                userDetailResponse.setModeratorList(moderatorDetailList);
            }
            if(request.recruiterIdList()!=null){
                List<Recruiter> recruiterList=recruiterRepository.findAllById(request.recruiterIdList());
                List<LoggedRecruiter> recruiterDetailList=recruiterList.stream().map(this::mapToLoggedRecruiter).toList();
                userDetailResponse.setRecruiterList(recruiterDetailList);
            }
            if(request.candidateIdList()!=null){
                List<Candidate> candidateList=candidateRepository.findAllById(request.candidateIdList());
                List<LoggedCandidate> candidateDetailList=candidateList.stream().map(this::mapToLoggedCandidate).toList();
                userDetailResponse.setCandidateList(candidateDetailList);
            }

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(userDetailResponse);

        }catch (Exception e){
            e.printStackTrace();
            errors.put("error","Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }



        return responseDTO;

    }

    private LoggedAdmin mapToLoggedAdmin(Admin res) {
        LoggedAdmin admin=modelMapper.map(res, LoggedAdmin.class);

        admin.setId(res.getUser().getId());
        admin.setEmail(res.getUser().getEmail());
        admin.setRole("admin");
        admin.setIsActive(res.getUser().getIsActive());
        admin.setCreatedAt(res.getUser().getCreatedAt().toString());

        return admin;
    }
    private LoggedModerator mapToLoggedModerator(Moderator res) {
        LoggedModerator moderator=modelMapper.map(res, LoggedModerator.class);

        moderator.setId(res.getUser().getId());
        moderator.setEmail(res.getUser().getEmail());
        moderator.setRole("moderator");
        moderator.setIsActive(res.getUser().getIsActive());
        moderator.setCreatedAt(res.getUser().getCreatedAt().toString());

        return moderator;
    }
    private LoggedRecruiter mapToLoggedRecruiter(Recruiter res) {
        LoggedRecruiter recruiter=modelMapper.map(res, LoggedRecruiter.class);

        recruiter.setId(res.getUser().getId());
        recruiter.setEmail(res.getUser().getEmail());
        recruiter.setRole("recruiter");
        recruiter.setIsActive(res.getUser().getIsActive());
        recruiter.setCreatedAt(res.getUser().getCreatedAt().toString());

        return recruiter;
    }
    private LoggedCandidate mapToLoggedCandidate(Candidate res) {
        LoggedCandidate candidate=modelMapper.map(res, LoggedCandidate.class);

        candidate.setId(res.getUser().getId());
        candidate.setEmail(res.getUser().getEmail());
        candidate.setRole("candidate");
        candidate.setIsActive(res.getUser().getIsActive());
        candidate.setCreatedAt(res.getUser().getCreatedAt().toString());

        return candidate;
    }



    public ResponseDTO getCandidateDetails(String candidateId) {
        var responseDTO=new ResponseDTO();
        var errors=new HashMap<String,String >();



        Candidate res=candidateRepository.findById(candidateId).orElse(null);
        if(res!=null){
            LoggedCandidate candidate=modelMapper.map(res,LoggedCandidate.class);

            candidate.setId(res.getUser().getId());
            candidate.setEmail(res.getUser().getEmail());
            candidate.setRole("candidate");
            candidate.setIsActive(res.getUser().getIsActive());
            candidate.setCreatedAt(res.getUser().getCreatedAt().toString());


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(candidate);
        }else{
            errors.put("candidate","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;
    }

    public ResponseDTO getRecruiterDetails(String recruiterId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Recruiter res=recruiterRepository.findById(recruiterId).orElse(null);
        if(res!=null){
            LoggedRecruiter recruiter=modelMapper.map(res, LoggedRecruiter.class);

            recruiter.setId(res.getUser().getId());
            recruiter.setEmail(res.getUser().getEmail());
            recruiter.setRole("recruiter");
            recruiter.setIsActive(res.getUser().getIsActive());
            recruiter.setCreatedAt(res.getUser().getCreatedAt().toString());


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(recruiter);
        }else{
            errors.put("recruiter","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }


    public ResponseDTO getModeratorDetails(String moderatorId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Moderator res=moderatorRepository.findById(moderatorId).orElse(null);
        if(res!=null){
            LoggedModerator moderator=modelMapper.map(res, LoggedModerator.class);

            moderator.setId(res.getUser().getId());
            moderator.setEmail(res.getUser().getEmail());
            moderator.setRole("moderator");
            moderator.setIsActive(res.getUser().getIsActive());
            moderator.setCreatedAt(res.getUser().getCreatedAt().toString());


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(moderator);
        }else{
            errors.put("recruiter","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }

    public ResponseDTO getAdminDetails(String adminId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Admin res=adminRepository.findById(adminId).orElse(null);
        if(res!=null){
            LoggedAdmin admin=modelMapper.map(res, LoggedAdmin.class);

            admin.setId(res.getUser().getId());
            admin.setEmail(res.getUser().getEmail());
            admin.setRole("admin");
            admin.setIsActive(res.getUser().getIsActive());
            admin.setCreatedAt(res.getUser().getCreatedAt().toString());


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(admin);
        }else{
            errors.put("recruiter","Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }


}
