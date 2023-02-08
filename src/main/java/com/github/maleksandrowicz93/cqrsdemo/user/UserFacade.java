package com.github.maleksandrowicz93.cqrsdemo.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    public List<StudentDto> getAllStudents() {
        return new ArrayList<>();
    }

    public StudentDto addStudent(AddStudentCommand command) {
        return new StudentDto();
    }

    public StudentDto getStudent(String studentId) {
        return new StudentDto();
    }

    public StudentDto updateStudent(String studentId, EditStudentCommand command) {
        return new StudentDto();
    }

    public void updatePassword(String studentId, String password) {}

    public void deleteStudent(String studentId) {}
}
