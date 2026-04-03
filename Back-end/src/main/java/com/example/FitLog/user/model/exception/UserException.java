package com.example.FitLog.user.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {

    private final HttpStatus status;

    public UserException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static UserException notFound() {
        return new UserException(HttpStatus.NOT_FOUND, "User not found");
    }

    public static UserException alreadyExists() {
        return new UserException(HttpStatus.CONFLICT, "User already exists");
    }

    public static UserException creationFailed(String reason) {
        return new UserException(HttpStatus.BAD_REQUEST, "User creation failed: " + reason);
    }
}
