package com.recruitease.user_detail_service.service;

import com.recruitease.user_detail_service.DTO.LoggedUser.LoggedAdmin;
import com.recruitease.user_detail_service.DTO.LoggedUser.LoggedCandidate;
import com.recruitease.user_detail_service.DTO.LoggedUser.LoggedModerator;
import com.recruitease.user_detail_service.DTO.LoggedUser.LoggedRecruiter;
import com.recruitease.user_detail_service.DTO.ResponseDTO;
import com.recruitease.user_detail_service.DTO.UserDetailsRequestDTO;
import com.recruitease.user_detail_service.DTO.UserDetailsResponseDTO;
import com.recruitease.user_detail_service.config.CustomUserDetails;
import com.recruitease.user_detail_service.entity.*;
import com.recruitease.user_detail_service.repository.AdminRepository;
import com.recruitease.user_detail_service.repository.CandidateRepository;
import com.recruitease.user_detail_service.repository.ModeratorRepository;
import com.recruitease.user_detail_service.repository.RecruiterRepository;
import com.recruitease.user_detail_service.util.CodeList;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ModelMapper modelMapper;
    private final AdminRepository adminRepository;
    private final ModeratorRepository moderatorRepository;

    public ResponseDTO getUserDetailsLists(UserDetailsRequestDTO request) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();
        var userDetailResponse = UserDetailsResponseDTO.builder()
                .recruiterIdList(request.recruiterIdList())
                .candidateIdList(request.candidateIdList())
                .moderatorIdList(request.moderatorIdList())
                .adminIdList(request.adminIdList())
                .build();

        try {
            if (request.adminIdList() != null) {
                List<Admin> adminList = adminRepository.findAllById(request.adminIdList());
                List<LoggedAdmin> adminDetailList = adminList.stream().map(this::mapToLoggedAdmin).toList();
                userDetailResponse.setAdminList(adminDetailList);
            }
            if (request.moderatorIdList() != null) {
                List<Moderator> moderatorList = moderatorRepository.findAllById(request.moderatorIdList());
                List<LoggedModerator> moderatorDetailList = moderatorList.stream().map(this::mapToLoggedModerator)
                        .toList();
                userDetailResponse.setModeratorList(moderatorDetailList);
            }
            if (request.recruiterIdList() != null) {
                List<Recruiter> recruiterList = recruiterRepository.findAllById(request.recruiterIdList());
                List<LoggedRecruiter> recruiterDetailList = recruiterList.stream().map(this::mapToLoggedRecruiter)
                        .toList();
                userDetailResponse.setRecruiterList(recruiterDetailList);
            }
            if (request.candidateIdList() != null) {
                List<Candidate> candidateList = candidateRepository.findAllById(request.candidateIdList());
                List<LoggedCandidate> candidateDetailList = candidateList.stream().map(this::mapToLoggedCandidate)
                        .toList();
                userDetailResponse.setCandidateList(candidateDetailList);
            }

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(userDetailResponse);

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;

    }

    private LoggedAdmin mapToLoggedAdmin(Admin res) {
        LoggedAdmin admin = modelMapper.map(res, LoggedAdmin.class);

        admin.setId(res.getUser().getId());
        admin.setEmail(res.getUser().getEmail());
        admin.setMobileNumber(res.getMobileNumber());
        admin.setRole("admin");
        admin.setIsActive(res.getUser().getIsActive());
        admin.setCreatedAt(res.getUser().getCreatedAt().toString());

        return admin;
    }

    private LoggedModerator mapToLoggedModerator(Moderator res) {
        LoggedModerator moderator = modelMapper.map(res, LoggedModerator.class);

        moderator.setId(res.getUser().getId());
        moderator.setEmail(res.getUser().getEmail());
        moderator.setMobileNumber(res.getMobileNumber());
        moderator.setRole("moderator");
        moderator.setIsActive(res.getUser().getIsActive());
        moderator.setCreatedAt(res.getUser().getCreatedAt().toString());

        return moderator;
    }

    private LoggedRecruiter mapToLoggedRecruiter(Recruiter res) {
        LoggedRecruiter recruiter = modelMapper.map(res, LoggedRecruiter.class);

        recruiter.setId(res.getUser().getId());
        recruiter.setEmail(res.getUser().getEmail());
        recruiter.setRole("recruiter");
        recruiter.setIsActive(res.getUser().getIsActive());
        recruiter.setCreatedAt(res.getUser().getCreatedAt().toString());

        return recruiter;
    }

    private LoggedCandidate mapToLoggedCandidate(Candidate res) {
        LoggedCandidate candidate = modelMapper.map(res, LoggedCandidate.class);

        candidate.setId(res.getUser().getId());
        candidate.setEmail(res.getUser().getEmail());
        candidate.setRole("candidate");
        candidate.setIsActive(res.getUser().getIsActive());
        candidate.setCreatedAt(res.getUser().getCreatedAt().toString());

        return candidate;
    }

    public ResponseDTO getCandidateDetails(String candidateId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Candidate res = candidateRepository.findById(candidateId).orElse(null);
        if (res != null) {
            LoggedCandidate candidate = modelMapper.map(res, LoggedCandidate.class);

            candidate.setId(res.getUser().getId());
            candidate.setEmail(res.getUser().getEmail());
            candidate.setRole("candidate");
            candidate.setIsActive(res.getUser().getIsActive());
            candidate.setCreatedAt(res.getUser().getCreatedAt().toString());

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(candidate);
        } else {
            errors.put("candidate", "Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;
    }

    public ResponseDTO getRecruiterDetails(String recruiterId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Recruiter res = recruiterRepository.findById(recruiterId).orElse(null);
        if (res != null) {
            LoggedRecruiter recruiter = modelMapper.map(res, LoggedRecruiter.class);

            recruiter.setId(res.getUser().getId());
            recruiter.setEmail(res.getUser().getEmail());
            recruiter.setRole("recruiter");
            recruiter.setIsActive(res.getUser().getIsActive());
            recruiter.setCreatedAt(res.getUser().getCreatedAt().toString());

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(recruiter);
        } else {
            errors.put("recruiter", "Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }

    public ResponseDTO getModeratorDetails(String moderatorId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Moderator res = moderatorRepository.findById(moderatorId).orElse(null);
        if (res != null) {
            LoggedModerator moderator = modelMapper.map(res, LoggedModerator.class);

            moderator.setId(res.getUser().getId());
            moderator.setEmail(res.getUser().getEmail());
            moderator.setRole("moderator");
            moderator.setIsActive(res.getUser().getIsActive());
            moderator.setCreatedAt(res.getUser().getCreatedAt().toString());

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(moderator);
        } else {
            errors.put("recruiter", "Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }

    public ResponseDTO getAdminDetails(String adminId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        Admin res = adminRepository.findById(adminId).orElse(null);
        if (res != null) {
            LoggedAdmin admin = modelMapper.map(res, LoggedAdmin.class);

            admin.setId(res.getUser().getId());
            admin.setEmail(res.getUser().getEmail());
            admin.setRole("admin");
            admin.setIsActive(res.getUser().getIsActive());
            admin.setCreatedAt(res.getUser().getCreatedAt().toString());

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(admin);
        } else {
            errors.put("recruiter", "Not found!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }
        return responseDTO;

    }

    @Transactional
    public ResponseDTO updateCandidate(Candidate candidatePutReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            // get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String candidateId = userDetails.getCandidateDetails().getCandidateId();
            System.out.printf("candiateId: %s", candidateId);

            candidatePutReq.setCandidateId(candidateId);
            Candidate prevData = candidateRepository.findById(candidateId).orElseThrow(AuthenticationException::new);
            ;
            prevData.updateObject(candidatePutReq);
            // dont have to call save method, transactional annotation update the db for us
            // if modified
            // Candidate updateResponse=candidateRepository.save(prevData);

            return getCandidateDetails(prevData.getCandidateId());

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateRecruiter(Recruiter recruiterPutReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            // get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String recruiterId = userDetails.getRecruiterDetails().getRecruiterId();
            System.out.printf("recruiterId: %s", recruiterId);

            recruiterPutReq.setRecruiterId(recruiterId);
            Recruiter prevData = recruiterRepository.findById(recruiterId).orElseThrow(AuthenticationException::new);
            ;
            prevData.updateObject(recruiterPutReq);
            // dont have to call save method, transactional annotation update the db for us
            // if modified
            // Candidate updateResponse=candidateRepository.save(prevData);

            return getRecruiterDetails(prevData.getRecruiterId());

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateModerator(Moderator moderatorPutReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            // get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String moderatorId = userDetails.getModeratorDetails().getModeratorId();
            System.out.printf("moderatorId: %s", moderatorId);

            moderatorPutReq.setModeratorId(moderatorId);
            Moderator prevData = moderatorRepository.findById(moderatorId).orElseThrow(AuthenticationException::new);
            ;
            prevData.updateObject(moderatorPutReq);
            // dont have to call save method, transactional annotation update the db for us
            // if modified
            // Candidate updateResponse=candidateRepository.save(prevData);

            return getModeratorDetails(prevData.getModeratorId());

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    @Transactional
    public ResponseDTO updateAdmin(Admin adminPutReq) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        try {

            // get canidate id of logged user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            String adminId = userDetails.getAdminDetails().getAdminId();
            System.out.printf("adminId: %s", adminId);

            adminPutReq.setAdminId(adminId);
            Admin prevData = adminRepository.findById(adminId).orElseThrow(AuthenticationException::new);
            ;
            prevData.updateObject(adminPutReq);
            // dont have to call save method, transactional annotation update the db for us
            // if modified
            // Candidate updateResponse=candidateRepository.save(prevData);

            return getAdminDetails(prevData.getAdminId());

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    public ResponseDTO getAllModerators() {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();


        try {

                List<Moderator> moderatorList = moderatorRepository.findAll();
                List<LoggedModerator> moderatorDetailList = moderatorList.stream().map(this::mapToLoggedModerator)
                        .toList();


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(moderatorDetailList);

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    public ResponseDTO getAllAdmins() {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();


        try {

            List<Admin> adminList = adminRepository.findAll();
            List<LoggedAdmin> adminListRes = adminList.stream().map(this::mapToLoggedAdmin)
                    .toList();


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(adminListRes);

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    public ResponseDTO getAllRecruiters() {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();


        try {

            List<Recruiter> recruiterList = recruiterRepository.findAll();
            List<LoggedRecruiter> recruiterListRes = recruiterList.stream().map(this::mapToLoggedRecruiter)
                    .toList();


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(recruiterListRes);

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    public ResponseDTO getAllCandidates() {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();


        try {

            List<Candidate> candidateList = candidateRepository.findAll();
            List<LoggedCandidate> candidateListRes = candidateList.stream().map(this::mapToLoggedCandidate)
                    .toList();


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(candidateListRes);

        } catch (Exception e) {
            e.printStackTrace();
            errors.put("error", "Error Occurred!");
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

}
