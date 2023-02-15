package com.github.maleksandrowicz93.cqrsdemo.student;

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
            return;
        }
        studentRepository.deleteById(studentId);
    }
}
