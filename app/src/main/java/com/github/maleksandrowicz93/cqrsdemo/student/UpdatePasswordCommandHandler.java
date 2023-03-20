package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.STUDENT_NOT_FOUND;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentIdentification> resultFactory;
    SecurityService securityService;

    ApiResult<StudentIdentification> handle(UpdatePasswordCommand command) {
        log.info("Handling; {}", command);
        var id = command.id();
        var password = command.password();
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", id);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        return studentQueryRepository.findById(id)
                .map(snapshot -> updatePassword(snapshot, password))
                .orElseGet(() -> notFoundResult(id));
    }

    private ApiResult<StudentIdentification> updatePassword(StudentSnapshot snapshot, String password) {
        var student = Student.fromSnapshot(snapshot);
        var encodedPassword = securityService.encodePassword(password);
        student.updatePassword(encodedPassword);
        var savedStudent = studentWriteRepository.save(student.createSnapshot());
        var studentIdentification = studentMapper.toStudentIdentification(savedStudent);
        log.info("Student's password updated successfully for: {}", studentIdentification);
        return resultFactory.create(studentIdentification);
    }

    private ApiResult<StudentIdentification> notFoundResult(UUID id) {
        log.error("Cannot add student with id {}, because not found", id);
        return resultFactory.create(STUDENT_NOT_FOUND);
    }
}
