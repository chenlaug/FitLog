package com.example.FitLog.Configuration;

import com.example.FitLog.exercise.model.exception.ExerciseException;
import com.example.FitLog.user.model.exception.UserException;
import com.example.FitLog.workout.model.exception.WorkoutException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionableHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> catchAny(Exception ex) {
        ProblemDetail pm = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(pm);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ProblemDetail> catchBadRequest(BadRequestException ex) {
        ProblemDetail pm = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(pm);
    }

    // User exception — gère tous les cas via le statut intégré dans UserException
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ProblemDetail> handleUserException(UserException ex) {
        ProblemDetail pm = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(pm);
    }

    // Exercise exception — gère tous les cas via le statut intégré dans ExerciseException
    @ExceptionHandler(ExerciseException.class)
    public ResponseEntity<ProblemDetail> handleExerciseException(ExerciseException ex) {
        ProblemDetail pm = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(pm);
    }

    // Workout exception — gère tous les cas via le statut intégré dans WorkoutException
    @ExceptionHandler(WorkoutException.class)
    public ResponseEntity<ProblemDetail> handleWorkoutException(WorkoutException ex) {
        ProblemDetail pm = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(pm);
    }

    // validation exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
