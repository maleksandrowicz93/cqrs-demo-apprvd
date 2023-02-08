package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;

class EditStudentDataCommandHandler {
    StudentDto handle(int studentId, EditStudentDataCommand command) {
        return StudentDto.builder().build();
    }
}
