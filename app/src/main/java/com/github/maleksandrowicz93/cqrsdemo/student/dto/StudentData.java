package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record StudentData(
        UUID id,
        String email,
        String firstName,
        String lastName,
        LocalDate birthDate
) {
}
