package com.github.maleksandrowicz93.cqrsdemo.student.adapters.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface StudentEntityMapper {

    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    StudentIdentification toStudentIdentification(StudentEntity student);

    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    StudentDto toStudentDto(StudentEntity student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(command.email())")
    @Mapping(target = "password", expression = "java(command.password())")
    @Mapping(target = "firstName", expression = "java(command.firstName())")
    @Mapping(target = "lastName", expression = "java(command.lastName())")
    @Mapping(target = "birthDate", expression = "java(command.birthDate())")
    Student toStudent(SaveStudentRequest command);

    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "password", expression = "java(student.password())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    Student toStudent(StudentEntity student);

    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "password", expression = "java(student.password())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    StudentEntity toStudentEntity(Student student);
}
