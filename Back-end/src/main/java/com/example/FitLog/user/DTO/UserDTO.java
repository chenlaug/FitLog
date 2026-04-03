package com.example.FitLog.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public class UserDTO {
//    @Data
//    @AllArgsConstructor
//    @Builder
//    public static class PostInput {
//        @NotBlank(message = "The user must have a name")
//        String name;
//
//        @NotBlank(message = "The user must have an email address")
//        @Email(message = "The email address is invalid")
//        String email;
//
//        @NotBlank(message = "Th user must have a password")
//        String password;
//    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class GetOutput
    {
        UUID id;
        String name;
        String email;
    }
}
