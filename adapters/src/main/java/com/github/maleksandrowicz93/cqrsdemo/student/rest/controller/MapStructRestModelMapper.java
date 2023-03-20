package com.github.maleksandrowicz93.cqrsdemo.student.rest.controller;

import com.github.maleksandrowicz93.cqrsdemo.repository.ResultPage;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentIdDto;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentPage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
interface MapStructRestModelMapper extends RestModelMapper {

    @Override
    @Mapping(target = "totalPages", expression = "java(resultPage.totalPages())")
    @Mapping(target = "students", expression = "java(toStudentIdStoList(resultPage.content()))")
    StudentPage toStudentPage(ResultPage<StudentIdentification> resultPage);

    List<StudentIdDto> toStudentIdStoList(List<StudentIdentification> studentIdentificationList);

    @Override
    @Mapping(target = "id", expression = "java(studentIdentification.id())")
    @Mapping(target = "email", expression = "java(studentIdentification.email())")
    StudentIdDto toStudentIdDto(StudentIdentification studentIdentification);

    @Override
    @Mapping(target = "id", expression = "java(studentData.id())")
    @Mapping(target = "email", expression = "java(studentData.email())")
    @Mapping(target = "firstName", expression = "java(studentData.firstName())")
    @Mapping(target = "lastName", expression = "java(studentData.lastName())")
    @Mapping(target = "birthDate", expression = "java(studentData.birthDate())")
    StudentDto toStudentDto(StudentData studentData);

    @Override
    @Mapping(target = "email", expression = "java(request.email())")
    @Mapping(target = "password", expression = "java(request.password())")
    @Mapping(target = "firstName", expression = "java(request.firstName())")
    @Mapping(target = "lastName", expression = "java(request.lastName())")
    @Mapping(target = "birthDate", expression = "java(request.birthDate())")
    AddStudentCommand toAddStudentCommand(SaveStudentRequest request);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", expression = "java(request.email())")
    @Mapping(target = "password", expression = "java(request.password())")
    @Mapping(target = "firstName", expression = "java(request.firstName())")
    @Mapping(target = "lastName", expression = "java(request.lastName())")
    @Mapping(target = "birthDate", expression = "java(request.birthDate())")
    EditStudentCommand toEditStudentCommand(UUID id, SaveStudentRequest request);

    @Override
    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", source = "password")
    UpdatePasswordCommand toUpdatePasswordCommand(UUID id, String password);

    @Override
    @Mapping(target = "id", source = "id")
    DeleteStudentCommand toDeleteStudentCommand(UUID id);
}
