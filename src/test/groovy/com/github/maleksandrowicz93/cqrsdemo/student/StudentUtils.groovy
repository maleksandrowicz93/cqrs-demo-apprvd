package com.github.maleksandrowicz93.cqrsdemo

import com.github.maleksandrowicz93.cqrsdemo.user.dto.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.user.dto.EditStudentDataCommand
import com.github.maleksandrowicz93.cqrsdemo.user.dto.StudentDto

import java.time.LocalDate
import java.time.Month

class StudentUtils {

    static final ID = 1
    static final EMAIL = "john.paul@gmail.com"
    static final FIRST_NAME = "John"
    static final LAST_NAME = "PAUL"
    static final PASSWORD = "password123"
    static final BIRTH_DATE = LocalDate.of(1993, Month.AUGUST, 21)

    static final ALTERNATIVE_ID = 2
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

    static def alternativeAddStudentCommand() {
        AddStudentCommand.builder()
                .email(ALTERNATIVE_EMAIL)
                .firstName(ALTERNATIVE_FIRST_NAME)
                .lastName(ALTERNATIVE_LAST_NAME)
                .birthDate(ALTERNATIVE_BIRTH_DAY)
                .password(ALTERNATIVE_PASSWORD)
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

    static def studentDto() {
        StudentDto.builder()
                .id(ID)
                .email(EMAIL)
                .firstName(FIRST_NAME)
                .lastName(LAST_NAME)
                .birthDate(BIRTH_DATE)
                .build()
    }

    static def alternativeStudentDto() {
        StudentDto.builder()
                .id(ALTERNATIVE_ID)
                .email(ALTERNATIVE_EMAIL)
                .firstName(ALTERNATIVE_FIRST_NAME)
                .lastName(ALTERNATIVE_LAST_NAME)
                .birthDate(ALTERNATIVE_BIRTH_DAY)
                .build()
    }
}
