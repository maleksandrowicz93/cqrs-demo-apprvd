package com.github.maleksandrowicz93.cqrsdemo.student.rest.controller;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentIdDto;

import java.util.UUID;

interface RestModelMapper {

    StudentIdDto toStudentIdDto(StudentIdentification studentIdentification);
    StudentDto toStudentDto(StudentData studentData);
    AddStudentCommand toAddStudentCommand(SaveStudentRequest request);
    EditStudentCommand toEditStudentCommand(UUID id, SaveStudentRequest request);
    UpdatePasswordCommand toUpdatePasswordCommand(UUID id, String password);
    DeleteStudentCommand toDeleteStudentCommand(UUID id);
}
