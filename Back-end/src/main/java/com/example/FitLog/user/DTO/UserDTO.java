package com.example.FitLog.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

public class UserDTO {
    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {
        String name;
        String email;
        String password;
    }

    public static class PostOutput {


    }
}
