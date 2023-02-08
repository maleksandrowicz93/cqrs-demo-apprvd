package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    STUDENT_ALREADY_EXISTS("Cannot add student because already exists.", HttpStatus.BAD_REQUEST),
    INVALID_CREDENTIALS("Username or password is invalid.", HttpStatus.BAD_REQUEST),
    STUDENT_NOT_FOUND("Student not found.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
