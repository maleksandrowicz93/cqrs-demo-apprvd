package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddStudentCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(AddStudentCommand command) throws InvalidCredentialsException, StudentAlreadyExistsException {
        String email = command.getEmail();
        boolean isEmailInvalid = email == null || email.isBlank();
        String password = command.getPassword();
        boolean isPasswordInvalid = password == null || password.isBlank();
        if (isEmailInvalid || isPasswordInvalid) {
            throw new InvalidCredentialsException();
        }
        if (studentRepository.existsByEmail(email)) {
            throw new StudentAlreadyExistsException();
        }
        Student student = StudentConverters.ADD_STUDENT_COMMAND_TO_STUDENT.convert(command);
        student = studentRepository.save(student);
        return StudentConverters.STUDENT_TO_STUDENT_DTO.convert(student);
    }
}
