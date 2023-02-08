package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto

import java.time.LocalDate
import java.time.Month

class StudentUtils {

    static final EMAIL = "john.paul@gmail.com"
    static final FIRST_NAME = "John"
    static final LAST_NAME = "Paul"
    static final PASSWORD = "password123"
    static final BIRTH_DATE = LocalDate.of(1993, Month.AUGUST, 21)

    static final ALTERNATIVE_EMAIL = "matty.cash@gmail.com"
    static final ALTERNATIVE_FIRST_NAME = "Matty"
    public static ALTERNATIVE_LAST_NAME = "Cash"
    public static ALTERNATIVE_PASSWORD = "PASSWORD-098"
    public static ALTERNATIVE_BIRTH_DAY = LocalDate.of(2000, Month.JANUARY, 1)

    private StudentUtils() {}

    static def addStudentCommand() {
        AddStudentCommand.builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .password(PASSWORD)
                .build()
    }

    static def editStudentDataCommand() {
        EditStudentDataCommand.builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .build()
    }

    static def studentDto(Long id) {
        StudentDto.builder()
                .id(id)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .build()
    }

    static def alternativeStudentDto(Long id) {
        StudentDto.builder()
                .id(id)
                .email(ALTERNATIVE_EMAIL)
                .firstName(ALTERNATIVE_FIRST_NAME)
                .lastName(ALTERNATIVE_LAST_NAME)
                .birthDate(ALTERNATIVE_BIRTH_DAY)
                .build()
    }

    static def studentToAdd() {
        Student.builder()
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .password(PASSWORD)
                .build()
    }

    static def alternativeStudentToAdd() {
        Student.builder()
                .email(ALTERNATIVE_EMAIL)
                .firstName(ALTERNATIVE_FIRST_NAME)
                .lastName(ALTERNATIVE_LAST_NAME)
                .birthDate(ALTERNATIVE_BIRTH_DAY)
                .password(ALTERNATIVE_PASSWORD)
                .build()
    }
}
