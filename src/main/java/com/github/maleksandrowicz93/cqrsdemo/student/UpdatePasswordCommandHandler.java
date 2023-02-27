package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.result.ApiResult;
import com.github.maleksandrowicz93.cqrsdemo.student.api.result.ApiResultFactory;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.SecurityService;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentMapper;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentQueryRepository;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentWriteRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.api.result.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.api.result.ResultCode.OK;
import static com.github.maleksandrowicz93.cqrsdemo.student.api.result.ResultCode.STUDENT_NOT_FOUND;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentIdentification> resultFactory;
    SecurityService securityService;

    ApiResult<StudentIdentification> handle(UUID studentId, String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", studentId);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        return studentQueryRepository.findById(studentId)
                .map(student -> student.password(securityService.encodePassword(password)))
                .map(studentWriteRepository::save)
                .map(studentMapper::toStudentIdentification)
                .map(student -> resultFactory.create(OK, student))
                .orElseGet(() -> resultFactory.create(STUDENT_NOT_FOUND));
    }
}
