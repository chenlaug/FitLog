package com.example.FitLog.workout.persistence;

import com.example.FitLog.workout.model.WorkoutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkoutRepository extends JpaRepository<WorkoutEntity, UUID> {
}
