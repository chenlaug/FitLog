package com.example.FitLog.user.service;

import com.example.FitLog.user.model.exception.UserCreationException;
import org.springframework.stereotype.Service;
import com.example.FitLog.user.persistence.UserRepository;
import com.example.FitLog.user.model.UserEntity;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String sayHello() {
        return "Hello World!";
    }

    public String createUser(String name) throws UserCreationException {
        System.out.println("Creating user: " + name);
        UserEntity user = UserEntity
                .builder()
                .name(name).build();

        userRepository.save(user);
        return  user.getName();
    }
}
