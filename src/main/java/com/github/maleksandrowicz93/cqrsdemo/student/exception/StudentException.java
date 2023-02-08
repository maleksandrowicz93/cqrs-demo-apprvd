package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import lombok.Getter;

@Getter
public abstract class StudentException extends Exception {

    private final ErrorMessage errorMessage;


    protected StudentException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}
