package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
class StudentExceptionHandlers {

    @ExceptionHandler(Throwable.class)
    ResponseEntity<Void> handle(Throwable e) {
        return ResponseEntity.internalServerError().build();
    }
}
