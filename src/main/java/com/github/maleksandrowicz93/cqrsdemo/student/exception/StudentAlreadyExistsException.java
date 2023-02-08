package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class StudentAlreadyExistsException extends StudentException {

    private static final ErrorMessage ERROR_MESSAGE = ErrorMessage.STUDENT_ALREADY_EXISTS;

    public StudentAlreadyExistsException() {
        super(ERROR_MESSAGE);
    }
}
