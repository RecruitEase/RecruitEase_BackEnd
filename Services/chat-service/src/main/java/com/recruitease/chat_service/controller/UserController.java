package com.recruitease.chat_service.controller;

import com.recruitease.chat_service.entity.User;
import com.recruitease.chat_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;


    //to save a user
    @MessageMapping("/user.addUser")
    @SendTo("/user/public")//queue for users status
    public User addUser(
            @Payload User user
    ){
        service.saveUser(user);
        return user;
    }

    //to disconnect user
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")//queue for users status
    public User disconnect(
            @Payload User user
    ){
        service.disconnect(user);
        return user;
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers(){
        return ResponseEntity.ok(service.findConnectedUsers());
    }

}
