package com.recruitease.file_service.controller;

import com.recruitease.file_service.DTO.ResponseDTO;
import com.recruitease.file_service.service.S3Service;
import com.recruitease.file_service.util.CodeList;
import com.recruitease.file_service.util.FileNameGenerator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final S3Service s3Service;


    //file upload download
    //piublic url is https://<bucket-name>.s3.amazonaws.com/<folder-path>/<image-name>
    @PostMapping(path = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseDTO> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("path") String path ) throws IOException {

        ResponseDTO res=s3Service.uploadFile(path, FileNameGenerator.generateUniqueFileName(Objects.requireNonNull(file.getOriginalFilename())),file);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res, HttpStatus.OK);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_RECRUITER','ROLE_CANDIDATE')")
    @RequestMapping(value = "/download/**", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(HttpServletRequest request) {

        String requestURL = request.getRequestURL().toString();

        String path = requestURL.split("/download/")[1];

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(s3Service.getFile(path).getObjectContent()));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR','ROLE_RECRUITER','ROLE_CANDIDATE')")
    @RequestMapping(value = "/view/**", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> viewFile(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/view/")[1];

        var s3Object = s3Service.getFile(path);
        var content = s3Object.getObjectContent();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG) // This content type can change by your file :)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+path+"\"")
                .body(new InputStreamResource(content));
    }


}
