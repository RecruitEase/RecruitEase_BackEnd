package com.recruitease.auth_service.controller;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.recruitease.auth_service.DTO.*;
import com.recruitease.auth_service.config.CustomUserDetails;
import com.recruitease.auth_service.service.AuthService;
import com.recruitease.auth_service.util.CodeList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;



    @PostMapping("/register-candidate")
    public ResponseEntity<ResponseDTO> registerCandidate(@RequestBody @Valid CandidateRequest request) {
        ResponseDTO res= authService.registerCandidate(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/register-recruiter")
    public ResponseEntity<ResponseDTO> registerRecruiter(@RequestBody @Valid RecruiterRequest request) {
        ResponseDTO res= authService.registerRecruiter(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }

    }



    @PostMapping("/register-admin")
    public ResponseEntity<ResponseDTO> registerAdmin(@RequestBody @Valid AdminModeratorRequest request) {
        ResponseDTO res= authService.registerAdmin(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register-moderator")
    public ResponseEntity<ResponseDTO> registerModerator(@RequestBody @Valid AdminModeratorRequest request) {

        ResponseDTO res= authService.registerModerator(request);
        if(res.getCode().equals(CodeList.RSP_SUCCESS)){

            return new ResponseEntity<>(res,HttpStatus.CREATED);

        }else{//some error

            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
    }


    //login?
    @PostMapping("/token")
    public ResponseEntity getToken(@RequestBody AuthRequest request) throws AuthenticationException {




        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        if(authenticate.isAuthenticated()){
            CustomUserDetails obj= (CustomUserDetails) authenticate.getPrincipal();


            SessionObjectResponse tokenRes=authService.generateSessionObj(obj.getId());


            return new ResponseEntity<>(tokenRes,HttpStatus.OK);
        }else{
            var responseDto=new ResponseDTO();
            responseDto.setErrors("Incorrect email or password");
            return new ResponseEntity<>(responseDto,HttpStatus.UNAUTHORIZED);
        }
    }


    //todo: token validation refine for springboot gateway
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        if(authService.validateToken(token)){
            return ResponseEntity.ok().body("Token is valid");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    //TODO: refresh token
//        @GetMapping("/refresh")
//        public ResponseEntity<String> validateToken(@RequestHeader
//                                                            Map<String, String> headers) {
//            headers.forEach((key, value) -> {
//                System.out.println(String.format("Header '%s' = %s", key, value));
//            });
//                return ResponseEntity.status(HttpStatus.OK).body("refresh");
//        }
}
