package com.github.maleksandrowicz93.cqrsdemo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentFacade {

    public List<StudentDto> getAllStudents() {
        return new ArrayList<>();
    }

    public StudentDto addStudent(AddStudentCommand command) {
        return StudentDto.builder().build();
    }

    public StudentDto getStudent(String studentId) {
        return StudentDto.builder().build();
    }

    public StudentDto editStudentData(String studentId, EditStudentDataCommand command) {
        return StudentDto.builder().build();
    }

    public boolean updatePassword(String studentId, String password) {
        return false;
    }

    public boolean deleteStudent(String studentId) {
        return false;
    }
}
