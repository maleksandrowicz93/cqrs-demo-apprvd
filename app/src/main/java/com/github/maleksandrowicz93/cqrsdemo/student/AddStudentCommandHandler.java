package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.OK;
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
        var email = command.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (StringUtils.isBlank(command.password())) {
            log.error("Password passed by {} should not be blank.", email);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        Optional<UUID> id = studentQueryRepository.findStudentIdByEmail(email);
        if (id.isPresent()) {
            var properties = Map.of(CONFLICTED_ID, id.get().toString());
            return resultFactory.create(STUDENT_ALREADY_EXISTS, properties);
        }
        var student = studentMapper.toStudent(command)
                .password(securityService.encodePassword(command.password()));
        var savedStudent = studentWriteRepository.save(student);
        var studentData = studentMapper.toStudentData(savedStudent);
        return resultFactory.create(OK, studentData);
    }
}
