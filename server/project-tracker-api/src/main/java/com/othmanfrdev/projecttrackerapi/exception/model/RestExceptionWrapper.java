package com.othmanfrdev.projecttrackerapi.exception.model;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
@Data
public class RestExceptionWrapper {
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus httpStatus;
    private Map<String, String> subErrors;
}
