package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record EditStudentCommand(
        UUID id,
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate birthDate
) {

    @Override
    public String toString() {
        return "EditStudentCommand[" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ']';
    }
}
