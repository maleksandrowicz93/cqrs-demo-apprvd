package com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing;

public interface SecurityService {

    String encodePassword(String password);
}
