package com.example.FitLog.exercise.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExerciseException extends RuntimeException {
    private final HttpStatus status;

    public ExerciseException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static ExerciseException notFound() {
        return new ExerciseException(HttpStatus.NOT_FOUND, "Exercise not found");
    }

    public static ExerciseException creationFailed(String reason) {
        return new ExerciseException(HttpStatus.BAD_REQUEST, "Exercise creation failed: " + reason);
    }

    public static ExerciseException updateFailed(String reason) {
        return new ExerciseException(HttpStatus.BAD_REQUEST, "Exercise update failed: " + reason);
    }
}
