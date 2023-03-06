package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.TestUtils
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand

import java.security.InvalidParameterException
import java.time.LocalDate
import java.time.Month

enum Students implements StudentFactory {

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

    final String email
    final String firstName
    final String lastName
    final String password
    final String encodedPassword
    final LocalDate birthDate

    Students(email, firstName, lastName, password, birthDate) {
        this.email = email
        this.firstName = firstName
        this.lastName = lastName
        this.password = password
        this.encodedPassword = TestUtils.encodePassword(this.password)
        this.birthDate = birthDate
    }

    static StudentFactory from(String email) {
        for (Students s : values()) {
            if (s.email == email) {
                return s;
            }
        }
        throw new InvalidParameterException("Student not defined in factory")
    }

    @Override
    StudentSnapshot studentToAdd() {
        addedStudent(null)
    }

    @Override
    StudentSnapshot addedStudent(UUID id) {
        StudentSnapshot.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .password(encodedPassword)
                .build()
    }

    @Override
    AddStudentCommand addStudentCommand() {
        AddStudentCommand.builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .password(password)
                .build()
    }

    @Override
    EditStudentCommand editStudentCommand(UUID id) {
        EditStudentCommand.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .password(password)
                .build()
    }

    @Override
    UpdatePasswordCommand updatePasswordCommand(UUID id) {
        return UpdatePasswordCommand.builder()
                .id(id)
                .password(password)
                .build()
    }

    @Override
    DeleteStudentCommand deleteStudentCommand(UUID id) {
        return DeleteStudentCommand.builder()
                .id(id)
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
    StudentData studentData(UUID id) {
        StudentData.builder()
                .id(id)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .build()
    }
}
