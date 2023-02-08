package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class InvalidCredentialsException extends StudentException {

    private static final ErrorMessage ERROR_MESSAGE = ErrorMessage.INVALID_CREDENTIALS;

    public InvalidCredentialsException() {
        super(ERROR_MESSAGE);
    }
}
