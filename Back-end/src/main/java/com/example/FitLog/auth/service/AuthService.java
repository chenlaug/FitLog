package com.example.FitLog.auth.service;

import com.example.FitLog.Configuration.JwtUtil;
import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.persistence.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                       UserRepository userRepository,
                       JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public AuthDTO.LoginOutput login(AuthDTO.LoginInput input) {
        // Vérifie email + password via Spring Security (lance une exception si invalide)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getEmail(), input.getPassword())
        );

        // Récupère l'utilisateur pour avoir son UUID
        UserEntity user = userRepository.findByEmail(input.getEmail())
                .orElseThrow();

        // Génère le token JWT avec l'UUID et l'email
        String token = jwtUtil.generateToken(user.getUuid(), user.getEmail());

        return AuthDTO.LoginOutput.builder()
                .token(token)
                .build();
    }
}
