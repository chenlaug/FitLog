package com.example.FitLog.workout.persistence;

import com.example.FitLog.workout.model.WorkoutExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExerciseEntity, UUID> {
}
