package com.othmanfrdev.projecttrackerapi.exception;

import com.othmanfrdev.projecttrackerapi.exception.model.RestExceptionWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<RestExceptionWrapper> handleEntityNotFoundException(EntityNotFoundException exception) {
        return buildResponseEntityExceptionWrapper(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProjectValidationException.class})
    public ResponseEntity<RestExceptionWrapper> handleProjectValidationException(ProjectValidationException exception) {
        return buildResponseEntityExceptionWrapper(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BudgetValidationException.class})
    public ResponseEntity<RestExceptionWrapper> handleBudgetValidationException(BudgetValidationException exception) {
        return buildResponseEntityExceptionWrapper(exception, HttpStatus.BAD_REQUEST);
    }

    private <T extends RuntimeException> ResponseEntity<RestExceptionWrapper> buildResponseEntityExceptionWrapper(T ex, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(
                        RestExceptionWrapper.builder()
                                .timestamp(LocalDateTime.now())
                                .message(ex.getMessage())
                                .httpStatus(httpStatus)
                                .build()
                );
    }
}
