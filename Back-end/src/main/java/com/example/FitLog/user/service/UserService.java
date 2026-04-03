package com.example.FitLog.user.service;

import com.example.FitLog.user.model.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.FitLog.user.persistence.UserRepository;
import com.example.FitLog.user.model.UserEntity;

import java.util.UUID;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity createUser(String name, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw UserException.alreadyExists();
        }
        if (name == null || name.isBlank()) {
            throw UserException.creationFailed("Name cannot be empty");
        }
        if (email == null || email.isBlank()) {
            throw UserException.creationFailed("Email cannot be empty");
        }

        UserEntity user = UserEntity.builder()
                .name(name)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        return userRepository.save(user);
    }

    public UserEntity findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(UserException::notFound);
    }

    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw UserException.notFound();
        }
        userRepository.deleteById(id);
    }

    public UserEntity updateById(UUID id, String name, String email) {
        UserEntity user = findById(id);
        if (user == null) {
            throw UserException.notFound();
        }

        UserEntity updated = UserEntity.builder()
                .uuid(user.getUuid())
                .name(name != null ? name : user.getName())
                .email(email != null ? email : user.getEmail())
                .password(user.getPassword())
                .build();
        return userRepository.save(updated);
    }
}
