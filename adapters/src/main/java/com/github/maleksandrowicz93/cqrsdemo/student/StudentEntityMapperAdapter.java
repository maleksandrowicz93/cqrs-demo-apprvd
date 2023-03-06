package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentEntityMapperAdapter implements StudentMapper {

    StudentEntityMapper studentEntityMapper;

    @Override
    public StudentIdentification toStudentIdentification(StudentSnapshot student) {
        var studentEntity = studentEntityMapper.toStudentEntity(student);
        return studentEntityMapper.toStudentIdentification(studentEntity);
    }

    @Override
    public StudentData toStudentData(StudentSnapshot student) {
        var studentEntity = studentEntityMapper.toStudentEntity(student);
        return studentEntityMapper.toStudentData(studentEntity);
    }

    @Override
    public StudentSnapshot toStudent(AddStudentCommand command) {
        return studentEntityMapper.toStudent(command);
    }

    @Override
    public StudentSnapshot toStudent(EditStudentCommand command) {
        return studentEntityMapper.toStudent(command);
    }
}
