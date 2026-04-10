package com.example.FitLog.auth.service;

import com.example.FitLog.Configuration.JwtUtil;
import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.auth.mapper.AuthMapper;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.model.exception.UserException;
import com.example.FitLog.user.persistence.UserRepository;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<AuthDTO.LoginOutput> login(String email, String password) {
        // Vérifie email + password via Spring Security (lance une exception si invalide)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(UserException::notFound);

        String token = jwtUtil.generateToken(user.getUuid(), user.getEmail());

        return ResponseEntity.ok(AuthMapper.toLoginOutput(token));
    }
}
