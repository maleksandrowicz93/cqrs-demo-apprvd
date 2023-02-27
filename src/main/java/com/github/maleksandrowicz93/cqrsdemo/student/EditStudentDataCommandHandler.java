package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.result.ApiResult;
import com.github.maleksandrowicz93.cqrsdemo.student.api.result.ApiResultFactory;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentData;
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
class EditStudentDataCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentData> resultFactory;
    SecurityService securityService;

    ApiResult<StudentData> handle(UUID studentId, SaveStudentRequest saveStudentRequest) {
        var email = saveStudentRequest.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (StringUtils.isBlank(saveStudentRequest.password())) {
            log.error("Password passed by {} should not be blank.", email);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (!studentQueryRepository.existsById(studentId)) {
            return resultFactory.create(STUDENT_NOT_FOUND);
        }
        var student = studentMapper.toStudent(saveStudentRequest)
                .id(studentId)
                .password(securityService.encodePassword(saveStudentRequest.password()));
        var savedStudent = studentWriteRepository.save(student);
        var StudentData = studentMapper.toStudentData(savedStudent);
        return resultFactory.create(OK, StudentData);
    }
}
