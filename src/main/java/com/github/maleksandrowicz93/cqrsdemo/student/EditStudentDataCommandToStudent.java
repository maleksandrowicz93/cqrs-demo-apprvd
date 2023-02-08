package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.Converter;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;

enum EditStudentDataCommandToStudent implements Converter<EditStudentDataCommand, Student> {

    INSTANCE;

    @Override
    public Student convert(EditStudentDataCommand command) {
        return Student.builder()
                .email(command.getEmail())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .birthDate(command.getBirthDate())
                .build();
    }
}
