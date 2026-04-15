package com.example.FitLog.user.mapper;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.model.UserEntity;

public class UserMapper {

    public static UserDTO.GetOutput getMeOutput(UserEntity user) {
        return UserDTO.GetOutput.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static UserDTO.PathOutput toPathOutput(UserEntity user) {
        return UserDTO.PathOutput.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    public static UserDTO.DeleteOutput toDeleteOutput(String message) {
        return UserDTO.DeleteOutput.builder().message(message).build();
    }
}
