package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.OK;
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
        var id = command.id();
        var password = command.password();
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", id);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        return studentQueryRepository.findById(id)
                .map(student -> student.password(securityService.encodePassword(password)))
                .map(studentWriteRepository::save)
                .map(studentMapper::toStudentIdentification)
                .map(student -> resultFactory.create(OK, student))
                .orElseGet(() -> resultFactory.create(STUDENT_NOT_FOUND));
    }
}
