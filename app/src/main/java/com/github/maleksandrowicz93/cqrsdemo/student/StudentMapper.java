package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;

interface StudentMapper {

    StudentIdentification toStudentIdentification(Student student);
    StudentData toStudentData(Student student);
    Student toStudent(AddStudentCommand command);
    Student toStudent(EditStudentCommand command);
}
