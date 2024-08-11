package com.recruitease.interview_service.service;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.DTO.ResponseDTO;
import com.recruitease.interview_service.model.Interview;
import com.recruitease.interview_service.repository.InterviewRepository;
import com.recruitease.interview_service.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ModelMapper modelMapper;
    private final ResponseDTO responseDTO;

    public ResponseDTO createInterview(InterviewDTO interviewDTO) {

            Interview interview=modelMapper.map(interviewDTO, Interview.class);
            Interview savedInterview = interviewRepository.save(interview);

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
