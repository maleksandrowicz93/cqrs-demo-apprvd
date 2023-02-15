package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    INVALID_CREDENTIALS("Username or password is invalid.", BAD_REQUEST),
    UNKNOWN_ERROR("Unknown error.", INTERNAL_SERVER_ERROR);

    final String message;
    final HttpStatus status;
}
