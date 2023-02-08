package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EditStudentDataCommandHandler {

    private final StudentRepository studentRepository;

    StudentDto handle(long studentId, EditStudentDataCommand command) {
        Student student = StudentConverters.EDIT_STUDENT_DATA_COMMAND_TO_STUDENT.convert(command);
        student.setId(studentId);
        student = studentRepository.save(student);
        return StudentConverters.STUDENT_TO_STUDENT_DTO.convert(student);
    }
}
