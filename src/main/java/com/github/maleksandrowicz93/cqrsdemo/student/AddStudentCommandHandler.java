package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class AddStudentCommandHandler {

    StudentRepository studentRepository;
    PasswordEncoder passwordEncoder;
    StudentMapper studentMapper;

    StudentDto handle(SaveStudentRequest saveStudentRequest) {
        var email = saveStudentRequest.email();
        if (StringUtils.isBlank(email)) {
            log.error("Email should not be blank.");
            throw new InvalidCredentialsException();
        }
        if (StringUtils.isBlank(saveStudentRequest.password())) {
            log.error("Password passed by {} should not be blank.", email);
            throw new InvalidCredentialsException();
        }
        if (studentRepository.existsByEmail(email)) {
            log.error("Student with email {} already exists", email);
            throw new StudentAlreadyExistsException();
        }
        var student = studentMapper.toStudent(saveStudentRequest);
        var encodedPassword = passwordEncoder.encode(student.password());
        student.password(encodedPassword);
        var savedStudent = studentRepository.save(student);
        return studentMapper.toStudentDto(savedStudent);
    }
}
