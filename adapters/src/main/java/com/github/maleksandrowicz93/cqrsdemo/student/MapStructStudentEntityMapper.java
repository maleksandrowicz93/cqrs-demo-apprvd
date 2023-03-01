package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface MapStructStudentEntityMapper extends StudentEntityMapper {

    @Override
    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    StudentIdentification toStudentIdentification(StudentEntity student);

    @Override
    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    StudentData toStudentData(StudentEntity student);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(command.email())")
    @Mapping(target = "password", expression = "java(command.password())")
    @Mapping(target = "firstName", expression = "java(command.firstName())")
    @Mapping(target = "lastName", expression = "java(command.lastName())")
    @Mapping(target = "birthDate", expression = "java(command.birthDate())")
    Student toStudent(AddStudentCommand command);

    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", expression = "java(command.email())")
    @Mapping(target = "password", expression = "java(command.password())")
    @Mapping(target = "firstName", expression = "java(command.firstName())")
    @Mapping(target = "lastName", expression = "java(command.lastName())")
    @Mapping(target = "birthDate", expression = "java(command.birthDate())")
    Student toStudent(EditStudentCommand command);

    @Override
    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "password", expression = "java(student.password())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    Student toStudent(StudentEntity student);

    @Override
    @Mapping(target = "id", expression = "java(student.id())")
    @Mapping(target = "email", expression = "java(student.email())")
    @Mapping(target = "password", expression = "java(student.password())")
    @Mapping(target = "firstName", expression = "java(student.firstName())")
    @Mapping(target = "lastName", expression = "java(student.lastName())")
    @Mapping(target = "birthDate", expression = "java(student.birthDate())")
    StudentEntity toStudentEntity(Student student);
}
