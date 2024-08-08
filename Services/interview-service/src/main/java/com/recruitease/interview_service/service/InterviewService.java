package com.recruitease.interview_service.service;

import com.recruitease.interview_service.DTO.InterviewDTO;
import com.recruitease.interview_service.model.Interview;
import com.recruitease.interview_service.repository.InterviewRepository;
import com.recruitease.interview_service.util.CodeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;


    public String createInterview(InterviewDTO interviewDTO) {

        if(interviewRepository.existsById(interviewDTO.getInterviewId())) {
            return CodeList.RSP_DUPLICATED;
        }else {
            Interview interview = convertToEntity(interviewDTO);
            interview.setInterviewId(sequenceGeneratorService.generateSequence(Interview.SEQUENCE_NAME));
            Interview savedInterview = interviewRepository.save(interview);

            if(savedInterview != null) {
                return CodeList.RSP_SUCCESS;
            }else {
                return CodeList.RSP_ERROR;
            }
        }
    }

    private Interview convertToEntity(InterviewDTO interviewDTO) {
        return Interview.builder()
                .applicationId(interviewDTO.getApplicationId())
                .type(interviewDTO.getType())
                .date(interviewDTO.getDate())
                .time(interviewDTO.getTime())
                .location(interviewDTO.getLocation())
                .dressCode(interviewDTO.getDressCode())
                .description(interviewDTO.getDescription())
                .cutoffDate(interviewDTO.getCutoffDate())
                .cutoffTime(interviewDTO.getCutoffTime())
                .build();
    }
}
