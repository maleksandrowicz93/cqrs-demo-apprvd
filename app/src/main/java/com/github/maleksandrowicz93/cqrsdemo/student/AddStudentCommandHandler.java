package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;
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
        var snapshot = studentMapper.toStudent(command);
        return Try.of(() -> tryToSaveStudent(snapshot))
                .onFailure(InvalidCredentialsException.class, e -> log.error(e.getMessage()))
                .getOrElse(() -> resultFactory.create(INVALID_CREDENTIALS));
    }

    private ApiResult<StudentData> tryToSaveStudent(StudentSnapshot snapshot) {
        Optional<UUID> id = studentQueryRepository.findStudentIdByEmail(snapshot.email());
        if (id.isPresent()) {
            Map<ResultProperty, String> properties = Map.of(CONFLICTED_ID, id.get().toString());
            return resultFactory.create(STUDENT_ALREADY_EXISTS, properties);
        }
        return saveStudent(snapshot);
    }

    private ApiResult<StudentData> saveStudent(StudentSnapshot snapshot) {
        Student student = Student.fromSnapshot(snapshot);
        String encodedPassword = securityService.encodePassword(snapshot.password());
        student.updatePassword(encodedPassword);
        StudentSnapshot savedStudent = studentWriteRepository.save(student.createSnapshot());
        StudentData studentData = studentMapper.toStudentData(savedStudent);
        return resultFactory.create(studentData);
    }
}
