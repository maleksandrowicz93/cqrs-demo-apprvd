package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder(toBuilder = true)
public class AddStudentCommand {

    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
