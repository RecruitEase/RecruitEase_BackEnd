package com.recruitease.interview_service.service;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.DTO.ResponseDTO;
import com.recruitease.interview_service.config.CustomUserDetails;
import com.recruitease.interview_service.model.Interview;
import com.recruitease.interview_service.repository.InterviewRepository;
import com.recruitease.interview_service.util.CodeList;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ModelMapper modelMapper;
    private final ResponseDTO responseDTO;

    public ResponseDTO getInterview() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        List<Interview> interviews;

        if(userDetails.getRole().equals("recruiter")){
            interviews = interviewRepository.findByRecruiterID(userDetails.getRecruiterDetails().getRecruiterId());

        } else if (userDetails.getRole().equals("candidate")) {
            interviews = interviewRepository.findByRecruiterID(userDetails.getCandidateDetails().getCandidateId());

        }else {
            responseDTO.setCode(CodeList.RSP_NOT_AUTHORISED);
            responseDTO.setContent(null);
            responseDTO.setErrors("Did not have permission to access this interviews");

            return responseDTO;
        }

        if (interviews == null || interviews.isEmpty()) {
            responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
        }else {
            responseDTO.setCode(CodeList.RSP_SUCCESS);
        }

        responseDTO.setContent(interviews);
        return responseDTO;

    }

    public ResponseDTO getInterviewByID(String interviewID) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Optional<Interview> interviewOptional = interviewRepository.findById(interviewID);

        if (interviewOptional.isEmpty()) {
            responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
            responseDTO.setMessage("Interview not found with the given ID.");
            responseDTO.setContent(null);
            return responseDTO;
        }
        Interview interview = interviewOptional.get();
        InterviewDTO interviewDTO = modelMapper.map(interview, InterviewDTO.class);

        if(userDetails.getRole().equals("recruiter") && userDetails.getRecruiterDetails().getRecruiterId().equals(interviewDTO.getRecruiterID())){
            responseDTO.setContent(interviewDTO);

        }else if(userDetails.getRole().equals("candidate") && userDetails.getCandidateDetails().getCandidateId().equals(interviewDTO.getCandidateID())){
            responseDTO.setContent(interviewDTO);

        }else {
            responseDTO.setCode(CodeList.RSP_NOT_AUTHORISED);
            responseDTO.setContent(null);
            responseDTO.setErrors("Did not have permission to access this interviews");

            return responseDTO;
        }

//        if (interviewDTO == null) {
//            responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
//        }else {
//            responseDTO.setCode(CodeList.RSP_SUCCESS);
//        }

        responseDTO.setContent(interviewDTO);
        responseDTO.setCode(CodeList.RSP_SUCCESS);
        responseDTO.setMessage("Interview found with the given ID.");
        return responseDTO;

    }

    public ResponseDTO createInterview(InterviewDTO interviewDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        interviewDTO.setRecruiterID(userDetails.getRecruiterDetails().getRecruiterId());
        Interview interview=modelMapper.map(interviewDTO, Interview.class);
        Interview savedInterview = interviewRepository.save(interview);

        ResponseDTO responseDTO = new ResponseDTO();

        if(savedInterview != null) {
                InterviewDTO result = modelMapper.map(savedInterview, InterviewDTO.class);
                responseDTO.setContent(result);
                responseDTO.setMessage("Interview created successfully");
                responseDTO.setCode(CodeList.RSP_SUCCESS);

        }else {
            responseDTO.setContent(null);
            responseDTO.setMessage("Error while creating interview");
            responseDTO.setCode(CodeList.RSP_ERROR);
        }
        return responseDTO;
    }



}
