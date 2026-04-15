package com.example.FitLog.exercise.controller;

import com.example.FitLog.exercise.DTO.ExerciseDTO;
import com.example.FitLog.exercise.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exercise")
public class ExerciseRestController {
    ExerciseService exerciseService;

    public ExerciseRestController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping("")
    public ResponseEntity<ExerciseDTO.PostOutput> createExercise(@Valid @RequestBody ExerciseDTO.PostInput input) {
        return exerciseService.createExercise(input.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExerciseDTO.DeleteOutput> deleteExercise(@PathVariable UUID id) {
        return exerciseService.deleteExercise(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDTO.GetOutput> getExercise(@PathVariable UUID id) {
        return exerciseService.getExerciseById(id);
    }

    @GetMapping("all")
    public ResponseEntity<Page<ExerciseDTO.GetOutput>> getAllExercises(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return exerciseService.getAllExercises(page, size);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExerciseDTO.PatchOutput> updateExercise(@PathVariable UUID id, @Valid @RequestBody ExerciseDTO.PatchInput input) {
        return exerciseService.updateExercise(id, input.getName());
    }
}
