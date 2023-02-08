package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class StudentQueryHandler {

    private final StudentRepository studentRepository;

    List<StudentDto> findAllStudents() {
        return studentRepository.findAll().stream()
                .map(StudentToStudentDto.INSTANCE::convert)
                .toList();
    }

    StudentDto findStudentById(long studentId) {
        return studentRepository.findById(studentId)
                .map(StudentToStudentDto.INSTANCE::convert)
                .orElse(null);
    }
}
