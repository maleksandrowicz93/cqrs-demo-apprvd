package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class EditStudentCommand {

    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
