package com.example.FitLog.workout.controller;

import com.example.FitLog.workout.service.WorkoutService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/workout")
public class WorkoutRestController {
    WorkoutService workoutService;

    public WorkoutRestController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }
}
