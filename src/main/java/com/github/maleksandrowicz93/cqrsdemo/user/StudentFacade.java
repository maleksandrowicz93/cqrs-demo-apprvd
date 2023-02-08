package com.github.maleksandrowicz93.cqrsdemo.user;

import com.github.maleksandrowicz93.cqrsdemo.user.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.user.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.user.dto.StudentDto;
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

    public StudentDto getStudent(int studentId) {
        return StudentDto.builder().build();
    }

    public StudentDto editStudentData(int studentId, EditStudentDataCommand command) {
        return StudentDto.builder().build();
    }

    public boolean updatePassword(int studentId, String password) {
        return false;
    }

    public boolean deleteStudent(int studentId) {
        return false;
    }
}
