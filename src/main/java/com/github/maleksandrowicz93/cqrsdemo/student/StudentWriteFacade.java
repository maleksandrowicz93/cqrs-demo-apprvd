package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.result.CommandHandlerResult;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentWriteFacade {

    AddStudentCommandHandler addStudentCommandHandler;
    EditStudentDataCommandHandler editstudentDataCommandHandler;
    UpdatePasswordCommandHandler updatePasswordCommandHandler;
    DeleteStudentCommandHandler deleteStudentCommandHandler;

    public CommandHandlerResult<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        return addStudentCommandHandler.handle(saveStudentRequest);
    }

    public CommandHandlerResult<StudentDto> editStudentData(UUID studentId, SaveStudentRequest saveStudentRequest) {
        return editstudentDataCommandHandler.handle(studentId, saveStudentRequest);
    }

    public CommandHandlerResult<StudentIdentification> updatePassword(UUID studentId, String password) {
        return updatePasswordCommandHandler.handle(studentId, password);
    }

    public void deleteStudent(UUID studentId) {
        deleteStudentCommandHandler.handle(studentId);
    }
}
