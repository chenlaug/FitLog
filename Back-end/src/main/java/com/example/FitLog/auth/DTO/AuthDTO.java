package com.example.FitLog.auth.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public class AuthDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class RegisterInput {
        @NotBlank(message = "The user must have a name")
        String name;

        @NotBlank(message = "The user must have an email address")
        @Email(message = "The email address is invalid")
        String email;

        @NotBlank(message = "Th user must have a password")
        String password;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class RegisterOutput {
        UUID id;
        String name;
        String email;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class LoginInput {
        @NotBlank(message = "The user must have an email address")
        @Email(message = "The email address is invalid")
        String email;

        @NotBlank(message = "Th user must have a password")
        String password;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class LoginOutput {
        String token;
    }
}
