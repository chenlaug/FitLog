package com.example.FitLog.workout.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WorkoutException extends RuntimeException {
    private final HttpStatus status;

    public WorkoutException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static WorkoutException notFound() {
        return new WorkoutException(HttpStatus.NOT_FOUND, "Workout not found");
    }

    public static WorkoutException creationFailed(String reason) {
        return new WorkoutException(HttpStatus.BAD_REQUEST, "Workout creation failed: " + reason);
    }

    public static WorkoutException forbidden() {
        return new WorkoutException(HttpStatus.FORBIDDEN, "Access denied to this workout");
    }
}
