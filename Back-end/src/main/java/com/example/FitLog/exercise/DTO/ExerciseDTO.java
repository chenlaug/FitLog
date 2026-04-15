package com.example.FitLog.exercise.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

public class ExerciseDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {
        @NotBlank
        String name;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput {
        UUID id;
        String name;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class DeleteOutput {
        String message;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetOutput {
        UUID id;
        String name;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PatchInput {
        String name;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PatchOutput {
        UUID id;
        String name;
    }
}
