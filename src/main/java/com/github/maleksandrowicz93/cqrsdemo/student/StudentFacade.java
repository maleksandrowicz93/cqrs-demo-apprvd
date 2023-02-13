package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class StudentFacade {

    final StudentQueryHandler studentQueryHandler;
    final AddStudentCommandHandler addStudentCommandHandler;
    final EditStudentDataCommandHandler editstudentDataCommandHandler;
    final UpdatePasswordCommandHandler updatePasswordCommandHandler;
    final DeleteStudentCommandHandler deleteStudentCommandHandler;

    public List<StudentIdentification> getAllStudents(int page, int size) {
        return studentQueryHandler.findAllStudents(page, size);
    }

    public StudentDto addStudent(SaveStudentRequest command) {
        return addStudentCommandHandler.handle(command);
    }

    public StudentDto getStudent(UUID studentId) {
        return studentQueryHandler.findStudentById(studentId);
    }

    public StudentDto editStudentData(UUID studentId, SaveStudentRequest command) {
        return editstudentDataCommandHandler.handle(studentId, command);
    }

    public void updatePassword(UUID studentId, String password) {
        updatePasswordCommandHandler.handle(studentId, password);
    }

    public void deleteStudent(UUID studentId) {
        deleteStudentCommandHandler.handle(studentId);
    }
}
