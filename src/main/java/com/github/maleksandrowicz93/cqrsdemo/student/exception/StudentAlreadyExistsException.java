package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class StudentAlreadyExistsException extends StudentException {

    public StudentAlreadyExistsException() {
        super(ErrorMessage.STUDENT_ALREADY_EXISTS);
    }
}
