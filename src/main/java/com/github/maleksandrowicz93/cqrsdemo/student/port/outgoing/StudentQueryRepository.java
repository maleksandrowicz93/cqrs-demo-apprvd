package com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.QueryRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentQueryRepository extends QueryRepository<Student, UUID> {

    Optional<UUID> findStudentIdByEmail(String email);
}
