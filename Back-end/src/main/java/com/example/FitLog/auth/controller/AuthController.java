package com.example.FitLog.auth.controller;

import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.auth.mapper.AuthMapper;
import com.example.FitLog.auth.service.AuthService;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthDTO.RegisterOutput> register(@Valid @RequestBody AuthDTO.RegisterInput input) {
        UserEntity newUser = userService.createUser(input.getName(), input.getEmail(), input.getPassword());
        return ResponseEntity.ok(AuthMapper.toRegisterOutput(newUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO.LoginOutput> login(@Valid @RequestBody AuthDTO.LoginInput input) {
        return ResponseEntity.ok(authService.login(input));
    }
}
