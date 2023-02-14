package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    StudentRepository studentRepository;
    PasswordEncoder passwordEncoder;

    void handle(UUID studentId, String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", studentId);
            throw new InvalidCredentialsException();
        }
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("Not found student with id: {}", studentId);
                    throw new StudentNotFoundException();
                });
        var encodedPassword = passwordEncoder.encode(password);
        student.password(encodedPassword);
        studentRepository.save(student);
    }
}
