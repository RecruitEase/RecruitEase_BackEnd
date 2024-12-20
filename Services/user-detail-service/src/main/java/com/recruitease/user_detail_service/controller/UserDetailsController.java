package com.recruitease.user_detail_service.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recruitease.user_detail_service.DTO.ResponseDTO;
import com.recruitease.user_detail_service.DTO.UserDetailsRequestDTO;
import com.recruitease.user_detail_service.entity.Admin;
import com.recruitease.user_detail_service.entity.Candidate;
import com.recruitease.user_detail_service.entity.Moderator;
import com.recruitease.user_detail_service.entity.Recruiter;
import com.recruitease.user_detail_service.service.UserService;
import com.recruitease.user_detail_service.util.CodeList;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserDetailsController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @PostMapping({ "/detail-list", "/recruiter-list", "/admin-list", "/moderator-list", "/candidate-list" })
    public ResponseEntity<ResponseDTO> userDetailList(@RequestBody UserDetailsRequestDTO request) {
        ResponseDTO res = userService.getUserDetailsLists(request);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> candidateDetails(@PathVariable String candidateId) {
        ResponseDTO res = userService.getCandidateDetails(candidateId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<ResponseDTO> recruiterDetails(@PathVariable String recruiterId) {
        ResponseDTO res = userService.getRecruiterDetails(recruiterId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/moderator/{moderatorId}")
    public ResponseEntity<ResponseDTO> moderatorDetails(@PathVariable String moderatorId) {
        ResponseDTO res = userService.getModeratorDetails(moderatorId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all/moderators")
    public ResponseEntity<ResponseDTO> moderatorAll() {
        ResponseDTO res = userService.getAllModerators();
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all/admin")
    public ResponseEntity<ResponseDTO> adminAll() {
        ResponseDTO res = userService.getAllAdmins();
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all/recruiter")
    public ResponseEntity<ResponseDTO> recruiterAll() {
        ResponseDTO res = userService.getAllRecruiters();
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all/candidate")
    public ResponseEntity<ResponseDTO> candidateAll() {
        ResponseDTO res = userService.getAllCandidates();
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin/{adminId}")
    public ResponseEntity<ResponseDTO> adminDetails(@PathVariable String adminId) {
        ResponseDTO res = userService.getAdminDetails(adminId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update-candidate")
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody Candidate candidatePutReq) {
        ResponseDTO res = userService.updateCandidate(candidatePutReq);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update-recruiter")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody Recruiter recruiterPutReq) {
        ResponseDTO res = userService.updateRecruiter(recruiterPutReq);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update-moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody Moderator moderatorPutReq) {
        ResponseDTO res = userService.updateModerator(moderatorPutReq);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO> updateProfile(@RequestBody Admin adminPutReq) {
        ResponseDTO res = userService.updateAdmin(adminPutReq);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/generic/user-details")
    public ResponseEntity<ResponseDTO> genericUserDetails(@RequestBody List<String> userIds) {
        ResponseDTO res = userService.getGenericUserDetails(userIds);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

    }
}
