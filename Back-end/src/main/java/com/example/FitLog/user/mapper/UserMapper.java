package com.example.FitLog.user.mapper;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.model.UserEntity;

public class UserMapper {
    public static UserDTO.PostOutput toPostOutput(UserEntity user) {
        return UserDTO.PostOutput.builder()
                .id(user.getUuid())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
