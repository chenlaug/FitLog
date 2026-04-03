package com.example.FitLog.auth.mapper;

import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.user.model.UserEntity;

public class AuthMapper {
    public static AuthDTO.RegisterOutput toRegisterOutput(UserEntity user) {
       return AuthDTO.RegisterOutput.builder()
               .id(user.getUuid())
               .email(user.getEmail())
               .name(user.getName())
               .build();
    }
}
