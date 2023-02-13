package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    STUDENT_ALREADY_EXISTS("Cannot add student because already exists.", BAD_REQUEST),
    INVALID_CREDENTIALS("Username or password is invalid.", BAD_REQUEST),
    STUDENT_NOT_FOUND("Student not found.", NOT_FOUND),
    PASSWORD_NOT_UPDATED("Password not updated. Insert a new value.", BAD_REQUEST),
    UNKNOWN_ERROR("Unknown error.", INTERNAL_SERVER_ERROR);

    final String message;
    final HttpStatus status;
}
