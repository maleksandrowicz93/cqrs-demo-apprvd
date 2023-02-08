package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EditStudentDataCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(long studentId, EditStudentDataCommand command) {
        Student student = EditStudentDataCommandToStudent.INSTANCE.convert(command);
        student.setId(studentId);
        student = studentRepository.save(student);
        return StudentToStudentDto.INSTANCE.convert(student);
    }
}
