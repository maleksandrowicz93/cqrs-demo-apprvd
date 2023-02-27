package com.github.maleksandrowicz93.cqrsdemo.student.adapter.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student;

interface StudentEntityMapper {

    StudentIdentification toStudentIdentification(StudentEntity student);
    StudentData toStudentData(StudentEntity student);
    Student toStudent(SaveStudentRequest command);
    Student toStudent(StudentEntity student);
    StudentEntity toStudentEntity(Student student);
}
