package com.example.FitLog.exercise.service;

import com.example.FitLog.exercise.DTO.ExerciseDTO;
import com.example.FitLog.exercise.mapper.ExerciseMapper;
import com.example.FitLog.exercise.model.ExerciseEntity;
import com.example.FitLog.exercise.model.exception.ExerciseException;
import com.example.FitLog.exercise.persistence.ExerciseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExerciseService {
    ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public ResponseEntity<ExerciseDTO.PostOutput> createExercise(String name) {
        if (name == null || name.isBlank()) {
            throw ExerciseException.creationFailed("Name cannot be blank");
        }
        ExerciseEntity newExercise = ExerciseEntity.builder().name(name).build();
        exerciseRepository.save(newExercise);
        return ResponseEntity.ok(ExerciseMapper.toPostOutput(newExercise));
    }

    public ResponseEntity<ExerciseDTO.DeleteOutput> deleteExercise(UUID id) {
        if (!exerciseRepository.existsById(id)) {
            throw ExerciseException.notFound();
        }
        exerciseRepository.deleteById(id);
        return ResponseEntity.ok(ExerciseMapper.toDeleteOutput("Exercise deleted successfully."));
    }

    public ResponseEntity<ExerciseDTO.GetOutput> getExerciseById(UUID id) {
        ExerciseEntity exercise = findById(id);
        return ResponseEntity.ok(ExerciseMapper.toGetOutput(exercise));
    }

    public ResponseEntity<Page<ExerciseDTO.GetOutput>> getAllExercises(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ExerciseDTO.GetOutput> result = exerciseRepository.findAll(pageable)
                .map(ExerciseMapper::toGetOutput);
        return ResponseEntity.ok(result);
    }

    public ResponseEntity<ExerciseDTO.PatchOutput> updateExercise(UUID id, String name) {
        ExerciseEntity exercise = findById(id);
        if (name != null && name.isBlank()) {
            throw ExerciseException.updateFailed("Name cannot be blank");
        }
        if (name != null) exercise.setName(name);
        exerciseRepository.save(exercise);
        return ResponseEntity.ok(ExerciseMapper.toPatchOutput(exercise));
    }

    private ExerciseEntity findById(UUID id) {
        return exerciseRepository.findById(id).orElseThrow(ExerciseException::notFound);
    }
}
