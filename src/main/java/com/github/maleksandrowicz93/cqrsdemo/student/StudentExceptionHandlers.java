package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.ErrorResponse;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.ErrorMessage;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
class StudentExceptionHandlers {

    @ExceptionHandler(StudentNotFoundException.class)
    ResponseEntity<ErrorResponse> handle(StudentNotFoundException e) {
        log.error(e);
        return buildErrorResponse(e.getErrorMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorMessage errorMessage) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorMessage.name())
                .message(errorMessage.getMessage())
                .build();
        return ResponseEntity.status(errorMessage.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(StudentException.class)
    ResponseEntity<ErrorResponse> handle(StudentException e) {
        log.error(e);
        return buildErrorResponse(e.getErrorMessage());
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ErrorResponse> handle(Throwable e) {
        ErrorMessage errorMessage = ErrorMessage.UNKNOWN_ERROR;
        log.error(errorMessage.getMessage(), e);
        return buildErrorResponse(errorMessage);
    }
}
