package com.example.FitLog.workout.service;

import com.example.FitLog.exercise.model.ExerciseEntity;
import com.example.FitLog.exercise.model.exception.ExerciseException;
import com.example.FitLog.exercise.persistence.ExerciseRepository;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.persistence.UserRepository;
import com.example.FitLog.workout.DTO.WorkoutDTO;
import com.example.FitLog.workout.mapper.WorkoutMapper;
import com.example.FitLog.workout.model.SetEntity;
import com.example.FitLog.workout.model.WorkoutEntity;
import com.example.FitLog.workout.model.WorkoutExerciseEntity;
import com.example.FitLog.workout.model.exception.WorkoutException;
import com.example.FitLog.workout.persistence.WorkoutExerciseRepository;
import com.example.FitLog.workout.persistence.WorkoutRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    public WorkoutService(WorkoutRepository workoutRepository,
                          WorkoutExerciseRepository workoutExerciseRepository,
                          ExerciseRepository exerciseRepository,
                          UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public WorkoutDTO.PostOutput createWorkout(String name, String description) {
        UserEntity user = userRepository.getReferenceById(getCurrentUserId());
        WorkoutEntity workout = WorkoutEntity.builder()
                .name(name)
                .description(description)
                .user(user)
                .build();
        workoutRepository.save(workout);
        return WorkoutMapper.toPostOutput(workout);
    }

    public WorkoutDTO.GetOutput getWorkout(UUID id) {
        WorkoutEntity workout = workoutRepository.findById(id)
                .orElseThrow(WorkoutException::notFound);
        if (!workout.getUser().getId().equals(getCurrentUserId())) {
            throw WorkoutException.forbidden();
        }
        return WorkoutMapper.toGetOutput(workout);
    }

    public void deleteWorkout(UUID id) {
        WorkoutEntity workout = workoutRepository.findById(id)
                .orElseThrow(WorkoutException::notFound);
        if (!workout.getUser().getId().equals(getCurrentUserId())) {
            throw WorkoutException.forbidden();
        }
        workoutRepository.delete(workout);
    }

    public WorkoutDTO.AddExerciseOutput addExercise(UUID workoutId, UUID exerciseId, List<WorkoutDTO.SetInput> sets) {
        WorkoutEntity workout = workoutRepository.findById(workoutId)
                .orElseThrow(WorkoutException::notFound);
        if (!workout.getUser().getId().equals(getCurrentUserId())) {
            throw WorkoutException.forbidden();
        }
        ExerciseEntity exercise = exerciseRepository.findById(exerciseId)
                .orElseThrow(ExerciseException::notFound);

        WorkoutExerciseEntity workoutExercise = WorkoutExerciseEntity.builder()
                .workout(workout)
                .exercise(exercise)
                .build();

        List<SetEntity> setEntities = sets.stream()
                .map(s -> SetEntity.builder()
                        .repetition(s.getRepetition())
                        .kg(s.getKg())
                        .workoutExercise(workoutExercise)
                        .build())
                .toList();

        workoutExercise.setSets(setEntities);
        workoutExerciseRepository.save(workoutExercise);

        return WorkoutMapper.toAddExerciseOutput(workoutExercise);
    }

    private UUID getCurrentUserId() {
        return (UUID) Objects.requireNonNull(
                SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
    }

    public void removeExercise(UUID workoutId, UUID workoutExerciseId) {
        WorkoutExerciseEntity workoutExercise = workoutExerciseRepository.findById(workoutExerciseId)
                .orElseThrow(WorkoutException::notFound);
        if (!workoutExercise.getWorkout().getId().equals(workoutId)) {
            throw WorkoutException.forbidden();
        }
        workoutExerciseRepository.delete(workoutExercise);
    }
}
