package com.github.maleksandrowicz93.cqrsdemo.student;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity(name = "student")
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    UUID id;
    @Column(unique = true, nullable = false)
    String email;
    @Column(nullable = false)
    String password;
    String firstName;
    String lastName;
    LocalDate birthDate;
}
