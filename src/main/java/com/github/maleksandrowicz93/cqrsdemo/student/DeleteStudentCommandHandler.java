package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
class DeleteStudentCommandHandler {

    final StudentRepository studentRepository;

    void handle(UUID studentId) {
        if (!studentRepository.existsById(studentId)) {
            log.error("Not found student with id: {}", studentId);
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(studentId);
    }
}
