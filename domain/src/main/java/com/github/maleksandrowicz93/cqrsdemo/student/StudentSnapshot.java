package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
record StudentSnapshot(
        UUID id,
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
