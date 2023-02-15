package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentFacade {

    StudentQueryHandler studentQueryHandler;
    AddStudentCommandHandler addStudentCommandHandler;
    EditStudentDataCommandHandler editstudentDataCommandHandler;
    UpdatePasswordCommandHandler updatePasswordCommandHandler;
    DeleteStudentCommandHandler deleteStudentCommandHandler;

    public List<StudentIdentification> getAllStudents(int page, int size) {
        return studentQueryHandler.findAllStudents(page, size);
    }

    public StudentDto addStudent(SaveStudentRequest saveStudentRequest) {
        return addStudentCommandHandler.handle(saveStudentRequest);
    }

    public Optional<StudentDto> getStudent(UUID studentId) {
        return studentQueryHandler.findStudentById(studentId);
    }

    public Optional<StudentDto> editStudentData(UUID studentId, SaveStudentRequest saveStudentRequest) {
        return editstudentDataCommandHandler.handle(studentId, saveStudentRequest);
    }

    public Optional<StudentIdentification> updatePassword(UUID studentId, String password) {
        return updatePasswordCommandHandler.handle(studentId, password);
    }

    public void deleteStudent(UUID studentId) {
        deleteStudentCommandHandler.handle(studentId);
    }
}
