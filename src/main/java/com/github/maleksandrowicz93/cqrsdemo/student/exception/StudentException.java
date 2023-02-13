package com.github.maleksandrowicz93.cqrsdemo.student.exception;

import lombok.Getter;

@Getter
abstract class StudentException extends RuntimeException {

    final ErrorMessage errorMessage;


    protected StudentException(ErrorMessage errorMessage) {
        super(errorMessage.message());
        this.errorMessage = errorMessage;
    }
}
