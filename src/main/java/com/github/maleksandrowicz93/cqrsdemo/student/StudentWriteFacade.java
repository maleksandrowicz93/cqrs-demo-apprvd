package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.result.ApiResult;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentWriteFacade {

    AddStudentCommandHandler addStudentCommandHandler;
    EditStudentDataCommandHandler editstudentDataCommandHandler;
    UpdatePasswordCommandHandler updatePasswordCommandHandler;
    DeleteStudentCommandHandler deleteStudentCommandHandler;

    public ApiResult<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        return addStudentCommandHandler.handle(saveStudentRequest);
    }

    public ApiResult<StudentDto> editStudentData(UUID studentId, SaveStudentRequest saveStudentRequest) {
        return editstudentDataCommandHandler.handle(studentId, saveStudentRequest);
    }

    public ApiResult<StudentIdentification> updatePassword(UUID studentId, String password) {
        return updatePasswordCommandHandler.handle(studentId, password);
    }

    public void deleteStudent(UUID studentId) {
        deleteStudentCommandHandler.handle(studentId);
    }
}
