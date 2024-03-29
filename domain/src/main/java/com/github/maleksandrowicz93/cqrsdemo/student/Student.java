package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@ToString
@Builder(access = PRIVATE)
@AllArgsConstructor(access = PRIVATE)
class Student {

    UUID id;
    String email;
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;

    static Student fromSnapshot(StudentSnapshot snapshot) {
        validateSnapshot(snapshot);
        return Student.builder()
                .id(snapshot.id())
                .email(snapshot.email())
                .password(snapshot.password())
                .firstName(snapshot.firstName())
                .lastName(snapshot.lastName())
                .birthDate(snapshot.birthDate())
                .build();
    }

    static void validateSnapshot(StudentSnapshot snapshot) {
        if (StringUtils.isBlank(snapshot.email())) {
            throw new InvalidCredentialsException("Email should not be blank.");
        }
        if (StringUtils.isBlank(snapshot.password())) {
            var message = String.format("Password passed by %s should not be blank.", snapshot.email());
            throw new InvalidCredentialsException(message);
        }
    }

    StudentSnapshot createSnapshot() {
        return StudentSnapshot.builder()
                .id(id)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .build();
    }

    void editData(StudentSnapshot snapshot) {
        validateSnapshot(snapshot);
        email = snapshot.email();
        password = snapshot.password();
        firstName = snapshot.firstName();
        lastName = snapshot.lastName();
        birthDate = snapshot.birthDate();
    }

    void updatePassword(String password) {
        if (StringUtils.isBlank(password)) {
            throw new InvalidCredentialsException("Cannot update password. Should not be blank.");
        }
        this.password = password;
    }
}
