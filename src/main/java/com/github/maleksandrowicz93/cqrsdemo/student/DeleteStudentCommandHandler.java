package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DeleteStudentCommandHandler {

    StudentRepository studentRepository;

    void handle(UUID studentId) {
        if (!studentRepository.existsById(studentId)) {
            log.error("Not found student with id: {}", studentId);
            throw new StudentNotFoundException();
        }
        studentRepository.deleteById(studentId);
    }
}
