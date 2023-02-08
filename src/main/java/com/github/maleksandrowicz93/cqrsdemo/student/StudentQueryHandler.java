package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class StudentQueryHandler {

    private final StudentRepository studentRepository;

    List<StudentDto> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentConverters.STUDENT_TO_STUDENT_DTO::convert)
                .toList();
    }

    StudentDto findStudentById(long studentId) throws StudentNotFoundException {
        return studentRepository.findById(studentId)
                .map(StudentConverters.STUDENT_TO_STUDENT_DTO::convert)
                .orElseThrow(StudentNotFoundException::new);
    }
}
