package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.Converter;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;

enum StudentToStudentDto implements Converter<Student, StudentDto> {

    INSTANCE;

    @Override
    public StudentDto convert(Student student) {
        return StudentDto.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .birthDate(student.getBirthDate())
                .build();
    }
}
