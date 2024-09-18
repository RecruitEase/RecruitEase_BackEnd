package com.recruitease.interview_service.service;

import com.recruitease.interview_service.DTO.*;
import com.recruitease.interview_service.config.CustomUserDetails;
import com.recruitease.interview_service.model.Interview;
import com.recruitease.interview_service.repository.InterviewRepository;
import com.recruitease.interview_service.util.CodeList;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


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
        ResponseDTO responseDTO = new ResponseDTO();


        if (userDetails.getRole().equals("recruiter")) {

            interviews = interviewRepository.findByRecruiterId(userDetails.getRecruiterDetails().getRecruiterId());

            if (interviews == null || interviews.isEmpty()) {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setContent(null);
            } else {
                List<InterviewResponseDTO> recruiterDTOs = interviews.stream()
                        .map(interview -> {
                            try {
                                InterviewResponseDTO dto = modelMapper.map(interview, InterviewResponseDTO.class);
                                String remainingDays = getRemaingDays(interview.getDate());
                                dto.setRemainingDays(remainingDays);
                                return dto;
                            } catch (Exception e) {
                                System.err.println("Mapping error: " + e.getMessage());
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setContent(recruiterDTOs);
            }

        } else if (userDetails.getRole().equals("candidate")) {
            interviews = interviewRepository.findByCandidateId(userDetails.getCandidateDetails().getCandidateId());
            if (interviews == null || interviews.isEmpty()) {
                responseDTO.setCode(CodeList.RSP_NO_DATA_FOUND);
                responseDTO.setContent(null);
            } else {
                List<InterviewResponseDTO> candidateDTOS = interviews.stream()
                        .map(interview -> {
                            try {
                                InterviewResponseDTO dto = modelMapper.map(interview, InterviewResponseDTO.class);
                                String remainingDays = getRemaingDays(interview.getDate());
                                dto.setRemainingDays(remainingDays);
                                return dto;
                            } catch (Exception e) {
                                System.err.println("Mapping error: " + e.getMessage());
                                e.printStackTrace();
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                responseDTO.setCode(CodeList.RSP_SUCCESS);
                responseDTO.setContent(candidateDTOS);
            }

        } else {
            responseDTO.setCode(CodeList.RSP_NOT_AUTHORISED);
            responseDTO.setContent(null);
            responseDTO.setErrors("Did not have permission to access these interviews");
            return responseDTO;
        }

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

        if(userDetails.getRole().equals("recruiter") && userDetails.getRecruiterDetails().getRecruiterId().equals(interview.getRecruiterId())){
            InterviewResponseDTO interviewDTO = modelMapper.map(interview, InterviewResponseDTO.class);
            responseDTO.setContent(interviewDTO);

        }else if(userDetails.getRole().equals("candidate") && userDetails.getCandidateDetails().getCandidateId().equals(interview.getCandidateId())){
            InterviewResponseDTO interviewDTO = modelMapper.map(interview, InterviewResponseDTO.class);
            responseDTO.setContent(interviewDTO);

        }else {
            responseDTO.setCode(CodeList.RSP_NOT_AUTHORISED);
            responseDTO.setContent(null);
            responseDTO.setErrors("Did not have permission to access this interviews");

            return responseDTO;
        }

        responseDTO.setCode(CodeList.RSP_SUCCESS);
        responseDTO.setMessage("Interview found with the given ID.");
        return responseDTO;

    }

    public ResponseDTO createInterview(InterviewDTO interviewDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Interview interview=modelMapper.map(interviewDTO, Interview.class);
        interview.setRecruiterId(userDetails.getRecruiterDetails().getRecruiterId());
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

    public ResponseDTO editInterview(String interviewID, EditDTO editDTO) {
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

        if (userDetails.getRecruiterDetails().getRecruiterId().equals(interview.getRecruiterId())) {
            modelMapper.map(editDTO, interview);
            Interview updatedInterview = interviewRepository.save(interview);
            InterviewResponseDTO result = modelMapper.map(updatedInterview, InterviewResponseDTO.class);

            responseDTO.setContent(result);
            responseDTO.setMessage("Interview updated successfully.");
            responseDTO.setCode(CodeList.RSP_SUCCESS);

        }else {
            responseDTO.setCode(CodeList.RSP_NOT_AUTHORISED);
            responseDTO.setContent(null);
            responseDTO.setErrors("You do not have permission to edit this interview.");
            return responseDTO;
        }

        return responseDTO;
    }

    private String getRemaingDays(String date) {
        LocalDate interviewDay = LocalDate.parse(date);
        long remainingDays = ChronoUnit.DAYS.between(LocalDate.now(), interviewDay);
        String remainDaysString = null;

        if (remainingDays > 0) {
            remainDaysString=(remainingDays + " days left");
        }else if (remainingDays == 0) {
            remainDaysString="Today";
        } else {
            remainDaysString="Overdue";
        }
        return remainDaysString;
    }




}
