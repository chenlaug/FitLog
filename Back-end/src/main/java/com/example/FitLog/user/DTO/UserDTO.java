package com.example.FitLog.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public class UserDTO {
    @Data
    @AllArgsConstructor
    @Builder
    public static class GetOutput
    {
        UUID id;
        String name;
        String email;
    }
    @Data
    @AllArgsConstructor
    @Builder
    public static class PatchInput {
        String name;
        String email;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PathOutput
    {
        UUID id;
        String name;
        String email;
    }
}
