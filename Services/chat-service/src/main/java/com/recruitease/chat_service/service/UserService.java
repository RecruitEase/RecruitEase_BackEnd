package com.recruitease.chat_service.service;

import com.recruitease.chat_service.entity.Status;
import com.recruitease.chat_service.entity.User;
import com.recruitease.chat_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    //to save a user
    public void saveUser(User user) {
        user.setStatus(Status.ONLINE);
        repository.save(user);
    }

    //to disconnect a user
    public void disconnect(User user) {
        var storedUser=repository.findById(user.getNickName())
                .orElse(null);
        if(storedUser!=null){
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    //to get connected users
    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }
}
