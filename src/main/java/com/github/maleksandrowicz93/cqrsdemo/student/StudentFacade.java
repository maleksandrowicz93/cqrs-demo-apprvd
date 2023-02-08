package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
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

    public StudentDto addStudent(AddStudentCommand command)
            throws InvalidCredentialsException, StudentAlreadyExistsException {
        return addStudentCommandHandler.handle(command);
    }

    public StudentDto getStudent(long studentId) throws StudentNotFoundException {
        return studentQueryHandler.findStudentById(studentId);
    }

    public StudentDto editStudentData(long studentId, EditStudentDataCommand command)
            throws InvalidCredentialsException, StudentNotFoundException {
        return editstudentDataCommandHandler.handle(studentId, command);
    }

    public boolean updatePassword(long studentId, String password)
            throws InvalidCredentialsException, StudentNotFoundException {
        return updatePasswordCommandHandler.handle(studentId, password);
    }

    public boolean deleteStudent(long studentId) throws StudentNotFoundException {
        return deleteStudentCommandHandler.handle(studentId);
    }
}
