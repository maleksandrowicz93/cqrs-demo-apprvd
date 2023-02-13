package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.PasswordNotUpdatedException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    final StudentRepository studentRepository;
    final PasswordEncoder passwordEncoder;

    void handle(UUID studentId, String password) {
        if (StringUtils.isBlank(password)) {
            log.error("Invalid password. Cannot be updated for student with id: {}.", studentId);
            throw new InvalidCredentialsException();
        }
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("Not found student with id: {}", studentId);
                    throw new StudentNotFoundException();
                });
        if (password.equals(student.password())) {
            throw new PasswordNotUpdatedException();
        }
        if (passwordEncoder.matches(password, student.password())) {
            throw new PasswordNotUpdatedException();
        }
        var encodedPassword = passwordEncoder.encode(password);
        student.password(encodedPassword);
        studentRepository.save(student);
    }
}
