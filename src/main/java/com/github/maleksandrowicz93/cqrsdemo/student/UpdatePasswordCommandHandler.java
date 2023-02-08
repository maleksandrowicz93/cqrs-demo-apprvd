package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    private final StudentRepository studentRepository;

    boolean handle(long studentId, String password) {
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setPassword(password);
                    studentRepository.save(student);
                    return true;
                })
                .orElse(false);
    }
}
