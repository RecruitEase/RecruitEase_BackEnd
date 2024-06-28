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

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    //to send response
    private final ResponseDTO responseDTO;


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


//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody @Valid AuthRequest request) {
//        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
//        if(authenticate.isAuthenticated()){
//            return ResponseEntity.ok(authService.generateToken(request.email()));
//        }else {
//            throw new RuntimeException("Invalid email or password");
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(authService.generateToken());
//    }

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

    //login?
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest request) {
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        System.out.println(request);
        if(authenticate.isAuthenticated()){
            CustomUserDetails obj= (CustomUserDetails) authenticate.getPrincipal();
            System.out.println(obj.getId());
            String jsonString = "{ \"name\": \"John Doe\",\n" +
                    "  \"email\": \"john.doe@example.com\",\n" +
                    "  \"picture\": \"https://example.com/avatar.jpg\",\n" +
                    "  \"sub\": \"1234567890\",\n" +
                    "  \"iat\": 1624366341,\n" +
                    "  \"exp\": 1624369941,\n" +
                    "  \"accessToken\": \"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI4NDljODk0MS03NzRlLTQ5Y2EtYTc0Mi1mZWFiNjc2OGM5MjgiLCJpYXQiOjE3MTk0MTExNTQsImV4cCI6MTcxOTQxMjk1NH0.rHzXmPqn4GG-6NxtFtUn7Eh5E2hu2cxX814KrnX1oToANLksUqIOneqPpdSfE2OM\",\n" +
                    "  \"refreshToken\": \"eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiI4NDljODk0MS03NzRlLTQ5Y2EtYTc0Mi1mZWFiNjc2OGM5MjgiLCJpYXQiOjE3MTk0MTExNTQsImV4cCI6MTcxOTQxMjk1NH0.rHzXmPqn4GG-6NxtFtUn7Eh5E2hu2cxX814KrnX1oToANLksUqIOneqPpdSfE2OM\"\n" +
                    "}";

            return ResponseEntity.ok(jsonString);
//            return ResponseEntity.ok(authService.generateToken(obj.getId()));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials!");
        }
    }



}
