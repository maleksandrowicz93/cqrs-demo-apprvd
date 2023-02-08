package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EditStudentDataCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(long studentId, EditStudentDataCommand command)
            throws InvalidCredentialsException, StudentNotFoundException {
        String email = command.getEmail();
        if (email == null || email.isEmpty()) {
            throw new InvalidCredentialsException();
        }
        return studentRepository.findById(studentId)
                .map(student -> {
                    Student newData = StudentConverters.EDIT_STUDENT_DATA_COMMAND_TO_STUDENT.convert(command);
                    newData.setId(studentId);
                    return newData;
                })
                .map(studentRepository::save)
                .map(StudentConverters.STUDENT_TO_STUDENT_DTO::convert)
                .orElseThrow(StudentNotFoundException::new);
    }
}
