package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Student {

    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
