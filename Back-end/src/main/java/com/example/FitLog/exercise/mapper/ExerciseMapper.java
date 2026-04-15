package com.example.FitLog.exercise.mapper;

import com.example.FitLog.exercise.DTO.ExerciseDTO;
import com.example.FitLog.exercise.model.ExerciseEntity;

public class ExerciseMapper {

    public static ExerciseDTO.PostOutput toPostOutput(ExerciseEntity exercise) {
        return ExerciseDTO.PostOutput.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .build();
    }

    public static ExerciseDTO.DeleteOutput toDeleteOutput(String message) {
        return ExerciseDTO.DeleteOutput.builder()
                .message(message)
                .build();
    }

    public static ExerciseDTO.GetOutput toGetOutput(ExerciseEntity exercise) {
        return ExerciseDTO.GetOutput.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .build();
    }

    public static ExerciseDTO.PatchOutput toPatchOutput(ExerciseEntity exercise) {
        return ExerciseDTO.PatchOutput.builder()
                .id(exercise.getId())
                .name(exercise.getName())
                .build();
    }
}
