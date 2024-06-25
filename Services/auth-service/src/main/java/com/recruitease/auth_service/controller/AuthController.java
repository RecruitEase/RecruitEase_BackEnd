package com.recruitease.auth_service.controller;

import com.recruitease.auth_service.DTO.AuthRequest;
import com.recruitease.auth_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.saveUser(request));
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
    public ResponseEntity<String> validateToken(@RequestParam("token") String token,@RequestParam String userID) {
        if(authService.validateToken(token,userID)){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest request) {
        Authentication authenticate= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        System.out.println(request.email());
        if(authenticate.isAuthenticated()){
            return ResponseEntity.ok(authService.generateToken(request.email()));
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect credentials!");
        }
    }



}
