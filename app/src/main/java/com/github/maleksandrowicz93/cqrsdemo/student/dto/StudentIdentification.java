package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record StudentIdentification(UUID id, String email) {
}
