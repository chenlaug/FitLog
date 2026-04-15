package com.example.FitLog.workout.controller;

import com.example.FitLog.workout.DTO.WorkoutDTO;
import com.example.FitLog.workout.service.WorkoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/workout")
public class WorkoutRestController {

    WorkoutService workoutService;

    public WorkoutRestController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping("")
    public ResponseEntity<WorkoutDTO.PostOutput> createWorkout(@Valid @RequestBody WorkoutDTO.PostInput input) {
        return ResponseEntity.ok(workoutService.createWorkout(input.getName(), input.getDescription()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO.GetOutput> getWorkout(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutService.getWorkout(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{workoutId}/exercise")
    public ResponseEntity<WorkoutDTO.AddExerciseOutput> addExercise(
            @PathVariable UUID workoutId,
            @Valid @RequestBody WorkoutDTO.AddExerciseInput input) {
        return ResponseEntity.ok(workoutService.addExercise(workoutId, input.getExerciseId(), input.getSets()));
    }

    @DeleteMapping("/{workoutId}/exercise/{workoutExerciseId}")
    public ResponseEntity<Void> removeExercise(
            @PathVariable UUID workoutId,
            @PathVariable UUID workoutExerciseId) {
        workoutService.removeExercise(workoutId, workoutExerciseId);
        return ResponseEntity.noContent().build();
    }
}
