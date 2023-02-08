package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    private final StudentRepository studentRepository;

    boolean handle(long studentId, String password) throws InvalidCredentialsException, StudentNotFoundException {
        if (password == null || password.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        return studentRepository.findById(studentId)
                .map(student -> {
                    student.setPassword(password);
                    studentRepository.save(student);
                    return true;
                })
                .orElseThrow(StudentNotFoundException::new);
    }
}
