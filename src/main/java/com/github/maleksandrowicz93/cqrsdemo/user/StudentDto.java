package com.github.maleksandrowicz93.cqrsdemo.user;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class StudentDto {
    private final String id;
    private final String email;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthDate;
}
