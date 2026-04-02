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

    public UserEntity createUser(String name,String email, String password) throws UserCreationException {
        UserEntity user = UserEntity
                .builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        userRepository.save(user);
        return  user;
    }
}
