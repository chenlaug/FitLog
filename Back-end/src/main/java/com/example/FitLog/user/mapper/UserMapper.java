package com.example.FitLog.user.mapper;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.model.UserEntity;

public class UserMapper {
    public static UserDTO.GetOutput toGetOutput(UserEntity user) {
        return UserDTO.GetOutput.builder()
                .id(user.getUuid())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
