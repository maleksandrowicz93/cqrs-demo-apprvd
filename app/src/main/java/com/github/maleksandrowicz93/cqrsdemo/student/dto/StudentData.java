package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class StudentData {

    UUID id;
    String email;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
