package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    private final StudentRepository studentRepository;

    boolean handle(int studentId, String password) {
        return false;
    }
}
