package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteStudentCommandHandler {

    private final StudentRepository studentRepository;

    boolean handle(int studentId) {
        return false;
    }
}
