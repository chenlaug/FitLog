package com.example.FitLog.workout.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

public class WorkoutDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostInput {
        @NotBlank
        String name;
        String description;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class PostOutput {
        UUID id;
        String name;
        String description;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetOutput {
        UUID id;
        String name;
        String description;
        List<WorkoutExerciseOutput> exercises;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class WorkoutExerciseOutput {
        UUID id;
        String exerciseName;
        List<SetOutput> sets;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class SetOutput {
        UUID id;
        int repetition;
        int kg;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class AddExerciseInput {
        @NotNull
        UUID exerciseId;
        @Valid
        @NotEmpty
        List<SetInput> sets;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class SetInput {
        @NotNull
        @Positive
        Integer repetition;
        @NotNull
        @PositiveOrZero
        Integer kg;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class AddExerciseOutput {
        UUID id;
        String exerciseName;
        List<SetOutput> sets;
    }
}
