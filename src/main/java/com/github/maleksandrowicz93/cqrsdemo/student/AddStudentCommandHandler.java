package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddStudentCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(AddStudentCommand command) {
        Student student = AddStudentCommandToStudent.INSTANCE.convert(command);
        student = studentRepository.save(student);
        return StudentToStudentDto.INSTANCE.convert(student);
    }
}
