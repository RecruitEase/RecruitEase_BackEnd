package com.recruitease.application_service.controller;

import com.recruitease.application_service.DTO.ApplicationRequest;
import com.recruitease.application_service.DTO.ApplicationUpdateRequestDTO;
import com.recruitease.application_service.DTO.ResponseDTO;
import com.recruitease.application_service.config.CustomUserDetails;
import com.recruitease.application_service.service.ApplicationService;
import com.recruitease.application_service.service.S3Service;
import com.recruitease.application_service.DTO.AtsRequest;
import com.recruitease.application_service.util.CodeList;
import com.recruitease.application_service.util.FileNameGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final S3Service s3Service;

    // create new application
    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    public ResponseEntity<ResponseDTO> createApplication(@RequestBody @Valid ApplicationRequest request) {
        ResponseDTO res = applicationService.createApplication(request);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // get application for the given application id
    @GetMapping("/view/{applicationId}")
    public ResponseEntity<ResponseDTO> getApplication(@PathVariable String applicationId) {
        ResponseDTO res = applicationService.getApplication(applicationId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // get applciations for a given candidate id
    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<ResponseDTO> getApplicationsPerCandidate(@PathVariable String candidateId) {
        ResponseDTO res = applicationService.getApplicationPerCandidate(candidateId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // get applications for a given job id
    @GetMapping("/job/{jobId}")
    public ResponseEntity<ResponseDTO> getApplicationsPerJob(@PathVariable String jobId) {
        ResponseDTO res = applicationService.getApplicationPerJob(jobId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // update application for given applicationid
    @PutMapping("/update/{applicationId}")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> updateApplication(@PathVariable String applicationId,
            @RequestBody ApplicationUpdateRequestDTO request) {
        ResponseDTO res = applicationService.updateApplicationStatus(applicationId, request);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {// some error
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // update application for list of ids
    @PutMapping("/updateBatch")
    @PreAuthorize("hasAnyRole('ROLE_RECRUITER')")
    public ResponseEntity<ResponseDTO> updateApplicationsBatch(@RequestBody ApplicationUpdateRequestDTO request) {
        ResponseDTO res = applicationService.updateApplicationStatusBatch(request);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // withdraw application
    @PutMapping("/withdraw/{applicationId}")
    @PreAuthorize("hasAnyRole('ROLE_CANDIDATE')")
    public ResponseEntity<ResponseDTO> withdrawApplication(@PathVariable String applicationId) {
        // get canidate id of logged user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String candidateId = userDetails.getCandidateDetails().getCandidateId();

        ResponseDTO res = applicationService.withdrawApplication(candidateId, applicationId);
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {// some error

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // track applicant history
    @PostMapping("/history")
    public ResponseEntity<ResponseDTO> getHistory(@RequestBody AtsRequest requestDTO) {
        ResponseDTO res = applicationService.getHistory(requestDTO.getCandidateId(), requestDTO.getRecruiterId());
        if (res.getCode().equals(CodeList.RSP_SUCCESS)) {

            return new ResponseEntity<>(res, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }
    }

    // example controller functions for uploads, user management context
    // etc......................................................................................

    // file upload download
    // piublic url is
    // https://<bucket-name>.s3.amazonaws.com/<folder-path>/<image-name>
    @PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

        s3Service.uploadFile("applications",
                FileNameGenerator.generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename())), file);
        return "File uploaded";
    }

    @GetMapping("/download/applications/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        fileName = "applications/" + fileName;
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(s3Service.getFile(fileName).getObjectContent()));
    }

    @GetMapping("/view/applications/{fileName}")
    public ResponseEntity<InputStreamResource> viewFile(@PathVariable String fileName) {
        fileName = "applications/" + fileName;
        var s3Object = s3Service.getFile(fileName);
        var content = s3Object.getObjectContent();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // This content type can change by your file :)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .body(new InputStreamResource(content));
    }

    // authorization examples.............................................

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String adminDetails() {
        return "admin";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public String moderatorDetails() {
        return "moderator";
    }

    @GetMapping("/candidate")
    @PreAuthorize("hasRole('ROLE_CANDIDATE')")
    public String candidateDetails() {
        return "candidate";
    }

    @GetMapping("/recruiter")
    @PreAuthorize("hasRole('ROLE_RECRUITER')")
    public String recruiterDetails() {
        return "recruiter";
    }

    // example of use=ing security context to get user details
    @GetMapping("/user")
    public String getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = userDetails.getUsername();
        String roles = userDetails.getAuthorities().toString();

        String additionalInfo;
        if (userDetails.getRole().equals("candidate")) {
            additionalInfo = userDetails.getCandidateDetails().getCandidateId();
        } else if (userDetails.getRole().equals("recruiter")) {
            additionalInfo = userDetails.getRecruiterDetails().getRecruiterId();
        } else if (userDetails.getRole().equals("admin")) {
            additionalInfo = userDetails.getAdminDetails().getAdminId();
        } else {
            additionalInfo = userDetails.getModeratorDetails().getModeratorId();
        }

        return "User ID: " + userId + ", Roles: " + roles + ", Additional Info: "
                + (additionalInfo != null ? additionalInfo : "N/A");
    }

}
