package com.github.maleksandrowicz93.cqrsdemo.student.adapter.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentEntityMapperAdapter implements StudentMapper {

    StudentEntityMapper studentEntityMapper;

    @Override
    public StudentIdentification toStudentIdentification(Student student) {
        StudentEntity studentEntity = studentEntityMapper.toStudentEntity(student);
        return studentEntityMapper.toStudentIdentification(studentEntity);
    }

    @Override
    public StudentDto toStudentDto(Student student) {
        StudentEntity studentEntity = studentEntityMapper.toStudentEntity(student);
        return studentEntityMapper.toStudentDto(studentEntity);
    }

    @Override
    public Student toStudent(SaveStudentRequest command) {
        return studentEntityMapper.toStudent(command);
    }
}
