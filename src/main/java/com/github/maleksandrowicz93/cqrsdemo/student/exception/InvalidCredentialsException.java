package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class InvalidCredentialsException extends StudentException {

    public InvalidCredentialsException() {
        super(ErrorMessage.INVALID_CREDENTIALS);
    }
}
