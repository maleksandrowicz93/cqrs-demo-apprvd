package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class PasswordNotUpdatedException extends StudentException {

    public PasswordNotUpdatedException() {
        super(ErrorMessage.PASSWORD_NOT_UPDATED);
    }
}
