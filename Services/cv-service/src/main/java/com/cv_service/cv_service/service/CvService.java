package com.cv_service.cv_service.service;

import com.cv_service.cv_service.DTO.CvDTO;
import com.cv_service.cv_service.DTO.CvSetResponseDTO;
import com.cv_service.cv_service.DTO.ResponseDTO;
import com.cv_service.cv_service.entity.Cv;
import com.cv_service.cv_service.repository.CvRepo;
import com.cv_service.cv_service.util.CodeList;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CvService {
    private final CvRepo cvRepo;
    private final ModelMapper modelMapper;

    public ResponseDTO saveCv(CvDTO req) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();


        //todo:get candidateid from logged user
        String candidateId = "3b0334d9-4464-4e7d-9d05-a2859a5a583a";


        if (cvRepo.existsByCvNameAndCandidateId(req.getCvName(), candidateId)) {
            errors.put("CvName", "CvName already exist");
            responseDTO.setErrors(errors);
            responseDTO.setCode(CodeList.RSP_DUPLICATED);
            responseDTO.setMessage("Error Occurred!");
        } else {
            Cv cv = modelMapper.map(req, Cv.class);
            cv.setCandidateId(candidateId);
            cvRepo.save(cv);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Cv saved Successfully!");
            responseDTO.setContent(cv.getCvId());

        }

        return responseDTO;
    }

    //get cv when cv id is given
    public ResponseDTO getCv(String cvId) {
        var responseDTO = new ResponseDTO();
        var errors = new HashMap<String, String>();

        if (cvRepo.existsById(cvId)) {
            Cv cv = cvRepo.findById(cvId).orElse(null);
            CvDTO cvDTO = modelMapper.map(cv, CvDTO.class);

            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(cvDTO);
        } else {
            errors.put("Cv", "Not found!");

            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(errors);
        }

        return responseDTO;
    }

    //get cvs for given candidateId
    public ResponseDTO getCvsPerCandidate(String candidateId){

        var responseDTO=new ResponseDTO();
        try{
            var res = cvRepo.findByCandidateId(candidateId)
                    .stream()
                    .map(source->modelMapper.map(source, Cv.class))
                    .toList();

            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception e){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }
        return responseDTO;
    }

    //get set of cvs for given set of ids
    public ResponseDTO getCvList(List<String> cvIds) {

        var responseDTO=new ResponseDTO();
        try {
            var res = cvRepo.findByCvIdIn(cvIds)
                    .stream()
                    .map(source->modelMapper.map(source,CvDTO.class))
                    .toList();


            responseDTO.setCode(CodeList.RSP_SUCCESS);
            responseDTO.setMessage("Success");
            responseDTO.setContent(res);
        }catch (Exception ex){
            responseDTO.setCode(CodeList.RSP_ERROR);
            responseDTO.setMessage("Error Occurred!");
            responseDTO.setErrors(null);
        }

        return responseDTO;
    }
}
