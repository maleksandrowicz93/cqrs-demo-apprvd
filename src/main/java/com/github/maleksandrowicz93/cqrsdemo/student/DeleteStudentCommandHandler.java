package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteStudentCommandHandler {

    private final StudentRepository studentRepository;

    boolean handle(long studentId) throws StudentNotFoundException {
        if (!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(studentId);
        return true;
    }
}
