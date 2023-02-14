package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
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
class EditStudentDataCommandHandler {

    StudentRepository studentRepository;
    PasswordEncoder passwordEncoder;
    StudentMapper studentMapper;

    StudentDto handle(UUID studentId, SaveStudentRequest command) {
        if (!studentRepository.existsById(studentId)) {
            log.error("Not found student with id: {}", studentId);
            throw new StudentNotFoundException();
        }
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("Not found student with id: {}", studentId);
                    throw new StudentNotFoundException();
                });
        if (StringUtils.isNotBlank(command.email())) {
            student.email(command.email());
        }
        if (StringUtils.isNotBlank(command.password())) {
            var encodedPassword = passwordEncoder.encode(student.password());
            student.password(encodedPassword);
        }
        if (StringUtils.isNotBlank(command.firstName())) {
            student.firstName(command.firstName());
        }
        if (StringUtils.isNotBlank(command.lastName())) {
            student.lastName(command.lastName());
        }
        if (command.birthDate() != null) {
            student.birthDate(command.birthDate());
        }
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentDto(savedStudent);
    }
}
