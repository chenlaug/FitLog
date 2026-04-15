package com.example.FitLog.auth.mapper;

import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.user.model.UserEntity;

public class AuthMapper {
    public static AuthDTO.RegisterOutput toRegisterOutput(UserEntity user) {
       return AuthDTO.RegisterOutput.builder()
               .id(user.getId())
               .email(user.getEmail())
               .name(user.getName())
               .build();
    }

    public static AuthDTO.LoginOutput toLoginOutput(String token) {
        return AuthDTO.LoginOutput.builder()
                .token(token)
                .build();
    }
}
