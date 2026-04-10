package com.example.FitLog.user.service;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.mapper.UserMapper;
import com.example.FitLog.user.model.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.FitLog.user.persistence.UserRepository;
import com.example.FitLog.user.model.UserEntity;

import java.util.Objects;
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

    public ResponseEntity<UserDTO.GetOutput> getMe() {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            throw UserException.canGetIdFromToken();
        }
        UserEntity user = findById(userId);
        return ResponseEntity.ok(UserMapper.getMeOutput(user));
    }

    public ResponseEntity<UserDTO.DeleteOutput> deleteById() {
        UUID userId = getCurrentUserId();
        if (!userRepository.existsById(userId)) {
            throw UserException.notFound();
        }
        userRepository.deleteById(userId);
        return ResponseEntity.ok(UserMapper.toDeleteOutput("User successfully deleted"));
    }

    public ResponseEntity<UserDTO.PathOutput> updateById(String name, String email) {
        UUID userId = getCurrentUserId();
        if (userId == null) {
            throw UserException.canGetIdFromToken();
        }

        UserEntity user = findById(userId);
        user.setName(name != null ? name : user.getName());
        user.setEmail(email != null ? email: user.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(UserMapper.toPathOutput(user));
    }


    // method to get current user id from security context
    public UUID getCurrentUserId() {
        return (UUID) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    // method to find user by id or throw not found exception
    public UserEntity findById(UUID id) {
        return userRepository.findById(id).orElseThrow(UserException::notFound);
    }
}
