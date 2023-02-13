package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
class StudentExceptionHandlers {

    @ExceptionHandler(StudentException.class)
    ResponseEntity<ErrorResponse> handle(StudentException e) {
        log.error(e);
        return buildErrorResponse(e.errorMessage());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(ErrorMessage errorMessage) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(errorMessage.name())
                .message(errorMessage.message())
                .build();
        return ResponseEntity.status(errorMessage.status()).body(errorResponse);
    }

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ErrorResponse> handle(Throwable e) {
        ErrorMessage errorMessage = ErrorMessage.UNKNOWN_ERROR;
        log.error(errorMessage.message(), e);
        return buildErrorResponse(errorMessage);
    }
}
