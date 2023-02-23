package com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.WriteRepository;

import java.util.UUID;

public interface StudentWriteRepository extends WriteRepository<Student, UUID> {
}
