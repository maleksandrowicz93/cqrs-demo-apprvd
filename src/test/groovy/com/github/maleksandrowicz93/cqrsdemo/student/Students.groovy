package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.time.LocalDate
import java.time.Month

enum Students implements StudentUtils {

    FIRST(
            "john.paul@gmail.com",
            "John",
            "Paul",
            "password123",
            LocalDate.of(1937, Month.APRIL, 21)
    ),
    SECOND(
            "matty.cash@gmail.com",
            "Matty",
            "Cash",
            "PASSWORD-098",
            LocalDate.of(2000, Month.JANUARY, 1)
    )

    final passwordEncoder = new BCryptPasswordEncoder()

    final String email
    final String firstName
    final String lastName
    final String password
    final String encodedPassword;
    final LocalDate birthDate

    Students(email, firstName, lastName, password, birthDate) {
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.password = password
        this.encodedPassword = passwordEncoder.encode(password);
        this.birthDate = birthDate
    }

    @Override
    SaveStudentRequest saveStudentRequest() {
        SaveStudentRequest.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .password(password)
                .build()
    }

    @Override
    StudentIdentification studentIdentification(UUID id) {
        StudentIdentification.builder()
                .id(id)
                .email(email)
                .build()
    }

    @Override
    StudentDto studentDto(UUID id) {
        StudentDto.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .build()
    }

    @Override
    Student studentToAdd() {
        Student.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .password(encodedPassword)
                .build()
    }
}
