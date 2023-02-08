package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class StudentNotFoundException extends StudentException {

    private static final ErrorMessage ERROR_MESSAGE = ErrorMessage.STUDENT_NOT_FOUND;

    public StudentNotFoundException() {
        super(ERROR_MESSAGE);
    }
}
