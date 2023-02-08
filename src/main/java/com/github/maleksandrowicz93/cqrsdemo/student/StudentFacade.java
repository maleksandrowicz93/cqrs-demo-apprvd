package com.github.maleksandrowicz93.cqrsdemo.user;

import com.github.maleksandrowicz93.cqrsdemo.user.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.user.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.user.dto.StudentDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentFacade {

    private final StudentQueryHandler studentQueryHandler;
    private final AddStudentCommandHandler addStudentCommandHandler;
    private final EditStudentDataCommandHandler editstudentDataCommandHandler;
    private final UpdatePasswordCommandHandler updatePasswordCommandHandler;
    private final DeleteStudentCommandHandler deleteStudentCommandHandler;

    public List<StudentDto> getAllStudents() {
        return studentQueryHandler.findAllStudents();
    }

    public StudentDto addStudent(AddStudentCommand command) {
        return addStudentCommandHandler.handle(command);
    }

    public StudentDto getStudent(int studentId) {
        return studentQueryHandler.findStudentById(studentId);
    }

    public StudentDto editStudentData(int studentId, EditStudentDataCommand command) {
        return editstudentDataCommandHandler.handle(studentId, command);
    }

    public boolean updatePassword(int studentId, String password) {
        return updatePasswordCommandHandler.handle(studentId, password);
    }

    public boolean deleteStudent(int studentId) {
        return deleteStudentCommandHandler.handle(studentId);
    }
}
