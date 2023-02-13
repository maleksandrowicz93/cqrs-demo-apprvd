package com.github.maleksandrowicz93.cqrsdemo.student.exception;

public class StudentNotFoundException extends StudentException {

    public StudentNotFoundException() {
        super(ErrorMessage.STUDENT_NOT_FOUND);
    }
}
