package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentFacade {

    AddStudentCommandHandler addStudentCommandHandler;
    EditStudentDataCommandHandler editstudentDataCommandHandler;
    UpdatePasswordCommandHandler updatePasswordCommandHandler;
    DeleteStudentCommandHandler deleteStudentCommandHandler;

    public ApiResult<StudentData> addStudent(AddStudentCommand command) {
        return addStudentCommandHandler.handle(command);
    }

    public ApiResult<StudentData> editStudentData(EditStudentCommand command) {
        return editstudentDataCommandHandler.handle(command);
    }

    public ApiResult<StudentIdentification> updatePassword(UpdatePasswordCommand command) {
        return updatePasswordCommandHandler.handle(command);
    }

    public void deleteStudent(DeleteStudentCommand command) {
        deleteStudentCommandHandler.handle(command);
    }
}
