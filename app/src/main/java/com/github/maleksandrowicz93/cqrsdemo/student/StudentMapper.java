package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;

interface StudentMapper {

    StudentIdentification toStudentIdentification(StudentSnapshot student);
    StudentData toStudentData(StudentSnapshot student);
    StudentSnapshot toStudent(AddStudentCommand command);
    StudentSnapshot toStudent(EditStudentCommand command);
}
