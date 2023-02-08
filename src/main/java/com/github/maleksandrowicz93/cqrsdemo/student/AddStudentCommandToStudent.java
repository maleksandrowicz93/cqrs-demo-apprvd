package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.Converter;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;

enum AddStudentCommandToStudent implements Converter<AddStudentCommand, Student> {

    INSTANCE;

    @Override
    public Student convert(AddStudentCommand command) {
        return Student.builder()
                .email(command.getEmail())
                .password(command.getPassword())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthDate(command.getBirthDate())
                .build();
    }
}
