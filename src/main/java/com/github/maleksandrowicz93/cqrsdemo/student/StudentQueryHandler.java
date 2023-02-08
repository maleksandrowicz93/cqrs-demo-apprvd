package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class StudentQueryHandler {

    private final StudentRepository studentRepository;

    List<StudentDto> findAllStudents() {
        return new ArrayList<>();
    }

    StudentDto findStudentById(int studentId) {
        return StudentDto.builder().build();
    }
}
