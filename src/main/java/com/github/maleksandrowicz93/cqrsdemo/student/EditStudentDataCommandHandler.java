package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class EditStudentDataCommandHandler {

    StudentWriteRepository studentWriteRepository;
    PasswordEncoder passwordEncoder;
    StudentMapper studentMapper;

    Optional<StudentDto> handle(UUID studentId, SaveStudentRequest saveStudentRequest) {
        var email = saveStudentRequest.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            throw new InvalidCredentialsException();
        }
        if (StringUtils.isBlank(saveStudentRequest.password())) {
            log.error("Password passed by {} should not be blank.", email);
            throw new InvalidCredentialsException();
        }
        if (!studentWriteRepository.existsById(studentId)) {
            return Optional.empty();
        }
        var student = studentMapper.toStudent(saveStudentRequest)
                .id(studentId)
                .password(passwordEncoder.encode(saveStudentRequest.password()));
        var savedStudent = studentWriteRepository.save(student);
        var studentDto = studentMapper.toStudentDto(savedStudent);
        return Optional.of(studentDto);
    }
}
