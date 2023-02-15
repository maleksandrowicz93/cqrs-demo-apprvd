package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
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
class UpdatePasswordCommandHandler {

    StudentWriteRepository studentWriteRepository;
    PasswordEncoder passwordEncoder;
    StudentMapper studentMapper;

    Optional<StudentIdentification> handle(UUID studentId, String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", studentId);
            throw new InvalidCredentialsException();
        }
        return studentWriteRepository.findById(studentId)
                .map(student -> student.password(passwordEncoder.encode(password)))
                .map(studentWriteRepository::save)
                .map(studentMapper::toStudentIdentification);
    }
}
