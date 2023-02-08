package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddStudentCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(AddStudentCommand command) {
        Student student = StudentConverters.ADD_STUDENT_COMMAND_TO_STUDENT.convert(command);
        student = studentRepository.save(student);
        return StudentConverters.STUDENT_TO_STUDENT_DTO.convert(student);
    }
}
