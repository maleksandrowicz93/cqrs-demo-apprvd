package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;

interface StudentEntityMapper {

    StudentIdentification toStudentIdentification(StudentEntity student);
    StudentData toStudentData(StudentEntity student);
    StudentSnapshot toStudent(AddStudentCommand command);
    StudentSnapshot toStudent(EditStudentCommand command);
    StudentSnapshot toStudent(StudentEntity student);
    StudentEntity toStudentEntity(StudentSnapshot student);
}
