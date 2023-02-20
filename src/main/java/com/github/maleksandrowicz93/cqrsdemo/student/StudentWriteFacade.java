package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Optional;
import java.util.UUID;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentWriteFacade {

    AddStudentCommandHandler addStudentCommandHandler;
    EditStudentDataCommandHandler editstudentDataCommandHandler;
    UpdatePasswordCommandHandler updatePasswordCommandHandler;
    DeleteStudentCommandHandler deleteStudentCommandHandler;

    public Optional<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        return addStudentCommandHandler.handle(saveStudentRequest);
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
