package com.cv_service.cv_service.controller;

import com.cv_service.cv_service.DTO.CvDTO;
import com.cv_service.cv_service.DTO.CvSetResponseDTO;
import com.cv_service.cv_service.DTO.ResponseDTO;
import com.cv_service.cv_service.service.CvService;
import com.cv_service.cv_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cv")
@RequiredArgsConstructor
public class CvController {

    private final CvService cvService;

    @PostMapping(value = "/saveCv")
    public ResponseEntity<ResponseDTO> saveCv(@RequestBody @Valid CvDTO cvDTO) {
            ResponseDTO res = cvService.saveCv(cvDTO);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/view/{cvId}")
    public ResponseEntity<ResponseDTO> getCv(@PathVariable String cvId) {
        ResponseDTO res = cvService.getCv(cvId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> getCvsPerCandidate(@PathVariable String candidateId) {
        ResponseDTO res = cvService.getCvsPerCandidate(candidateId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseDTO> getCvList(@RequestBody List<String> cvIds) {
        ResponseDTO res = cvService.getCvList(cvIds);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }
}
