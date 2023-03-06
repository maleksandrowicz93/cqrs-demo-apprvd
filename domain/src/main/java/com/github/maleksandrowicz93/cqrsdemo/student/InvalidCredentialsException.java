package com.github.maleksandrowicz93.cqrsdemo.student;

class InvalidCredentialsException extends RuntimeException {

    InvalidCredentialsException(String message) {
        super(message);
    }
}
