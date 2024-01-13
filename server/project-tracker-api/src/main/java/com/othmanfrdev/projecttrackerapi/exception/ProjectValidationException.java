package com.othmanfrdev.projecttrackerapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProjectValidationException extends RuntimeException {
    public ProjectValidationException() {
    }

    public ProjectValidationException(String message) {
        super(message);
    }
}
