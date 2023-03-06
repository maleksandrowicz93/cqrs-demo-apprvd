package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
class StudentSnapshot {

    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
