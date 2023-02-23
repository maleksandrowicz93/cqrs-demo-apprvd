package com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;

public interface StudentMapper {

    StudentIdentification toStudentIdentification(Student student);

    StudentDto toStudentDto(Student student);

    Student toStudent(SaveStudentRequest command);
}
