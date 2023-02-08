package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;

class AddStudentCommandHandler {
    StudentDto handle(AddStudentCommand command) {
        return StudentDto.builder().build();
    }
}
