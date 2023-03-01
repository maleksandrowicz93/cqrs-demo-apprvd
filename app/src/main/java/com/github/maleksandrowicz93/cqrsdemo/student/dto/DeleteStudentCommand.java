package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class DeleteStudentCommand {

    UUID id;
}
