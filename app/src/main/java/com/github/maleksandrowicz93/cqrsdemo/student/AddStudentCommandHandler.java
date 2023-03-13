package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.STUDENT_ALREADY_EXISTS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty.CONFLICTED_ID;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class AddStudentCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentData> resultFactory;
    SecurityService securityService;

    ApiResult<StudentData> handle(AddStudentCommand command) {
        log.info("Handling; {}", command);
        var snapshot = studentMapper.toStudent(command);
        return Try.run(() -> Student.validateSnapshot(snapshot))
                .map(success -> tryToAddStudent(snapshot))
                .onFailure(InvalidCredentialsException.class, e -> log.error("Cannot add student, {}", e.getMessage()))
                .getOrElse(() -> resultFactory.create(INVALID_CREDENTIALS));
    }

    private ApiResult<StudentData> tryToAddStudent(StudentSnapshot snapshot) {
        return studentQueryRepository.findStudentIdByEmail(snapshot.email())
                .map(this::conflictResult)
                .orElseGet(() -> addStudent(snapshot));
    }

    private ApiResult<StudentData> conflictResult(UUID id) {
        log.error("Cannot add student, because already exist with id {}", id);
        var properties = Map.of(CONFLICTED_ID, id.toString());
        return resultFactory.create(STUDENT_ALREADY_EXISTS, properties);
    }

    private ApiResult<StudentData> addStudent(StudentSnapshot snapshot) {
        var student = Student.fromSnapshot(snapshot);
        var encodedPassword = securityService.encodePassword(snapshot.password());
        student.updatePassword(encodedPassword);
        var savedStudent = studentWriteRepository.save(student.createSnapshot());
        var studentData = studentMapper.toStudentData(savedStudent);
        log.info("Student successfully saved: {}", studentData);
        return resultFactory.create(studentData);
    }
}
