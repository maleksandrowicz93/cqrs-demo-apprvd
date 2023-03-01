package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Data;
import lombok.Value;

import java.util.UUID;

@Value
@Data
public class StudentIdentification {

    UUID id;
    String email;
}
