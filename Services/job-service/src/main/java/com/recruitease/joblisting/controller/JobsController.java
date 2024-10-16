package com.recruitease.joblisting.controller;

import com.recruitease.joblisting.dto.JobRequest;
import com.recruitease.joblisting.dto.JobResponse;
import com.recruitease.joblisting.dto.Response;

import com.recruitease.joblisting.dto.ResponseDTO;
import com.recruitease.joblisting.model.Job;
import com.recruitease.joblisting.service.JobService;
import com.recruitease.joblisting.util.CodeList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Validated
public class JobsController {

    private final JobService jobService;


    @PostMapping
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<Response> createJob(@RequestBody JobRequest jobRequest) {
        Response response = jobService.createJob(jobRequest);
        if (response.getCode().equals("201")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/update-job")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> updateJob(@RequestBody Job jobPutReq) {
        ResponseDTO res= jobService.updateJob(jobPutReq);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete-job/{jobId}")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> deleteJob(@PathVariable String jobId) {
        ResponseDTO res= jobService.deleteJob(jobId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }


    //get job for the given job id
    @GetMapping("/view/{jobId}")
    public ResponseEntity<ResponseDTO> getJob(@PathVariable String jobId){
        ResponseDTO res= jobService.getJob(jobId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllJobs() {
        ResponseDTO res= jobService.getAllJobs();
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //public api to get jobs for each recruiter - only live jobs are given
    @GetMapping("/get-live-jobs-by-recruiter/{recruiterId}")
    public ResponseEntity<ResponseDTO> getLiveJobsByRecruiter(@PathVariable String recruiterId) {
        ResponseDTO res= jobService.getLiveJobsByRecruiterId(recruiterId);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    //private api for recruiters to get their own jobs despite the status
    @GetMapping("/get-my-jobs")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> getJobsByRecruiter() {
        ResponseDTO res= jobService.getJobsByRecruiterId();
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }



   
}
