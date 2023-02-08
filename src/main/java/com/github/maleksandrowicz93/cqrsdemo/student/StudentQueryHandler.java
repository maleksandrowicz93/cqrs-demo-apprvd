package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;

import java.util.ArrayList;
import java.util.List;

class StudentQueryHandler {

    List<StudentDto> findAllStudents() {
        return new ArrayList<>();
    }

    StudentDto findStudentById(int studentId) {
        return StudentDto.builder().build();
    }
}
