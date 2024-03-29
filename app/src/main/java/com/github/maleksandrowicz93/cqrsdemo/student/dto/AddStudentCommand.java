package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record AddStudentCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        LocalDate birthDate
) {

    @Override
    public String toString() {
        return "AddStudentCommand[" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ']';
    }
}
