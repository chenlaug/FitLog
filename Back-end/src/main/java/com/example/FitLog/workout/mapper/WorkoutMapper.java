package com.example.FitLog.workout.mapper;

import com.example.FitLog.workout.DTO.WorkoutDTO;
import com.example.FitLog.workout.model.SetEntity;
import com.example.FitLog.workout.model.WorkoutEntity;
import com.example.FitLog.workout.model.WorkoutExerciseEntity;

import java.util.List;

public class WorkoutMapper {

    public static WorkoutDTO.PostOutput toPostOutput(WorkoutEntity workout) {
        return WorkoutDTO.PostOutput.builder()
                .id(workout.getId())
                .name(workout.getName())
                .description(workout.getDescription())
                .build();
    }

    public static WorkoutDTO.GetOutput toGetOutput(WorkoutEntity workout) {
        List<WorkoutDTO.WorkoutExerciseOutput> exercises = workout.getExercises().stream()
                .map(WorkoutMapper::toWorkoutExerciseOutput)
                .toList();
        return WorkoutDTO.GetOutput.builder()
                .id(workout.getId())
                .name(workout.getName())
                .description(workout.getDescription())
                .exercises(exercises)
                .build();
    }

    public static WorkoutDTO.WorkoutExerciseOutput toWorkoutExerciseOutput(WorkoutExerciseEntity we) {
        List<WorkoutDTO.SetOutput> sets = we.getSets().stream()
                .map(WorkoutMapper::toSetOutput)
                .toList();
        return WorkoutDTO.WorkoutExerciseOutput.builder()
                .id(we.getId())
                .exerciseName(we.getExercise().getName())
                .sets(sets)
                .build();
    }

    public static WorkoutDTO.SetOutput toSetOutput(SetEntity set) {
        return WorkoutDTO.SetOutput.builder()
                .id(set.getId())
                .repetition(set.getRepetition())
                .kg(set.getKg())
                .build();
    }

    public static WorkoutDTO.AddExerciseOutput toAddExerciseOutput(WorkoutExerciseEntity we) {
        List<WorkoutDTO.SetOutput> sets = we.getSets().stream()
                .map(WorkoutMapper::toSetOutput)
                .toList();
        return WorkoutDTO.AddExerciseOutput.builder()
                .id(we.getId())
                .exerciseName(we.getExercise().getName())
                .sets(sets)
                .build();
    }
}
