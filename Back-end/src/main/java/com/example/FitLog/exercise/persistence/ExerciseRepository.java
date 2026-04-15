package com.example.FitLog.exercise.persistence;

import com.example.FitLog.exercise.model.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExerciseRepository extends JpaRepository<ExerciseEntity, UUID> {
}
