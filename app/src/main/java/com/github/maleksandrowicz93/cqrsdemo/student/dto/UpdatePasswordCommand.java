package com.github.maleksandrowicz93.cqrsdemo.student.dto;

import lombok.Builder;

import java.util.UUID;

@Builder(toBuilder = true)
public record UpdatePasswordCommand(UUID id, String password) {

    @Override
    public String toString() {
        return "UpdatePasswordCommand[" +
                "id=" + id +
                ']';
    }
}
