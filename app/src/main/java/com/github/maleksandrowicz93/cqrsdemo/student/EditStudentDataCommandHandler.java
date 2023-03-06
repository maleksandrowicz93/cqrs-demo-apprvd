package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.STUDENT_NOT_FOUND;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class EditStudentDataCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentData> resultFactory;
    SecurityService securityService;

    ApiResult<StudentData> handle(EditStudentCommand command) {
        var snapshot = studentMapper.toStudent(command);
        return Try.run(() -> Student.validateSnapshot(snapshot))
                .map(success -> tryToEditStudent(snapshot))
                .onFailure(InvalidCredentialsException.class, e -> log.error(e.getMessage()))
                .getOrElse(() -> resultFactory.create(INVALID_CREDENTIALS));
    }

    private ApiResult<StudentData> tryToEditStudent(StudentSnapshot snapshot) {
        if (!studentQueryRepository.existsById(snapshot.id())) {
            return resultFactory.create(STUDENT_NOT_FOUND);
        }
        return editStudent(snapshot);
    }

    private ApiResult<StudentData> editStudent(StudentSnapshot snapshot) {
        Student student = Student.fromSnapshot(snapshot);
        String encodedPassword = securityService.encodePassword(snapshot.password());
        student.updatePassword(encodedPassword);
        var savedStudent = studentWriteRepository.save(student.createSnapshot());
        var studentData = studentMapper.toStudentData(savedStudent);
        return resultFactory.create(studentData);
    }
}
