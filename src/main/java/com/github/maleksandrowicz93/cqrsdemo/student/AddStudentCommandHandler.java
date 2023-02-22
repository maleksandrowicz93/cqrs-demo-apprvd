package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.result.CommandHandlerResult;
import com.github.maleksandrowicz93.cqrsdemo.student.result.CommandHandlerResultFactory;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.result.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.result.ResultCode.OK;
import static com.github.maleksandrowicz93.cqrsdemo.student.result.ResultCode.STUDENT_ALREADY_EXISTS;
import static com.github.maleksandrowicz93.cqrsdemo.student.result.ResultProperty.CONFLICTED_ID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class AddStudentCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    PasswordEncoder passwordEncoder;
    StudentMapper studentMapper;
    CommandHandlerResultFactory<StudentDto> resultFactory;

    CommandHandlerResult<StudentDto> handle(SaveStudentRequest saveStudentRequest) {
        var email = saveStudentRequest.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        if (StringUtils.isBlank(saveStudentRequest.password())) {
            log.error("Password passed by {} should not be blank.", email);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        Optional<UUID> id = studentQueryRepository.findStudentIdByEmail(email);
        if (id.isPresent()) {
            var properties = Collections.singletonMap(CONFLICTED_ID, id.get().toString());
            return resultFactory.create(STUDENT_ALREADY_EXISTS, properties);
        }
        var student = studentMapper.toStudent(saveStudentRequest)
                .password(passwordEncoder.encode(saveStudentRequest.password()));
        var savedStudent = studentWriteRepository.save(student);
        var studentDto = studentMapper.toStudentDto(savedStudent);
        return resultFactory.create(studentDto, OK);
    }
}
