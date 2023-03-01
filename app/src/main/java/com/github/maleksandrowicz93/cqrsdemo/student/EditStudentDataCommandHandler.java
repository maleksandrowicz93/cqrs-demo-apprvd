package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
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
class EditStudentDataCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentData> resultFactory;
    SecurityService securityService;

    ApiResult<StudentData> handle(EditStudentCommand command) {
        var id = command.id();
        var email = command.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (StringUtils.isBlank(command.password())) {
            log.error("Password passed by {} should not be blank.", email);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (!studentQueryRepository.existsById(id)) {
            return resultFactory.create(STUDENT_NOT_FOUND);
        }
        var student = studentMapper.toStudent(command)
                .id(id)
                .password(securityService.encodePassword(command.password()));
        var savedStudent = studentWriteRepository.save(student);
        var StudentData = studentMapper.toStudentData(savedStudent);
        return resultFactory.create(OK, StudentData);
    }
}
