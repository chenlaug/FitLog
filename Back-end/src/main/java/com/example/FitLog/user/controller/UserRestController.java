package com.example.FitLog.user.controller;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.mapper.UserMapper;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO.GetOutput> getMe() {
        // Récupère l'UUID stocké dans le SecurityContext par le JwtAuthFilter
        UUID userId = (UUID) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        UserEntity user = userService.findById(userId);
        return ResponseEntity.ok(UserMapper.toGetOutput(user));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteMe() {
        UUID userId = (UUID) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        userService.deleteById(userId);
        return ResponseEntity.ok("User successfully deleted");
    }

    @PatchMapping("")
    public ResponseEntity<UserDTO.PathOutput> updateUser(@Valid @RequestBody UserDTO.PatchInput input) {
        UUID userId = (UUID) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        UserEntity updated = userService.updateById(userId, input.getName(), input.getEmail());
        return ResponseEntity.ok(UserMapper.toPathOutput(updated));
    }
}
