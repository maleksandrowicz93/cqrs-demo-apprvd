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
        return new StudentDto();
    }

    public StudentDto getStudent(String studentId) {
        return new StudentDto();
    }

    public StudentDto editStudentData(String studentId, EditStudentCommand command) {
        return new StudentDto();
    }

    public boolean updatePassword(String studentId, String password) {
        return false;
    }

    public boolean deleteStudent(String studentId) {
        return false;
    }
}
