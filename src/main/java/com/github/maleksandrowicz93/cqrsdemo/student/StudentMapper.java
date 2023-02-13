package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface StudentMapper {

    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    StudentIdentification toStudentIdentification(Student student);


    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    StudentDto toStudentDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(command.email())")
    @Mapping(target = "password", expression = "java(command.password())")
    @Mapping(target = "firstName", expression = "java(command.firstName())")
    @Mapping(target = "lastName", expression = "java(command.lastName())")
    @Mapping(target = "birthDate", expression = "java(command.birthDate())")
    Student toStudent(SaveStudentRequest command);
}
