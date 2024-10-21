package com.recruitease.application_service.controller;

import com.recruitease.application_service.DTO.*;
import com.recruitease.application_service.service.OfferService;
import com.recruitease.application_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> createOffer(@RequestBody @Valid OfferRequest request){
        ResponseDTO res= offerService.createOffer(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get offer for the given offerId id
    @GetMapping("/view/{offerId}")
    public ResponseEntity<ResponseDTO> getOffer(@PathVariable String offerId){
        ResponseDTO res= offerService.getOffer(offerId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get offers for a given candidate id
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> getOffersPerCandidate(@PathVariable String candidateId){
        ResponseDTO res= offerService.getOffersPerCandidate(candidateId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get offers for a given recruiter id
    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<ResponseDTO> getOffersPerRecruiter(@PathVariable String recruiterId){
        ResponseDTO res= offerService.getOffersPerRecruiter(recruiterId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //get offers for a given job id
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ResponseDTO> getOfferPerJob(@PathVariable String jobId){
        ResponseDTO res= offerService.getOffersPerJob(jobId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }


    //update offerstatus for  offerId
    @PutMapping("/update/{offerId}")
    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE','ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> updateOffer(@PathVariable String offerId,@RequestBody OfferUpdateRequest request){
        ResponseDTO res= offerService.updateOffer(offerId,request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }else{//some error
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

}
