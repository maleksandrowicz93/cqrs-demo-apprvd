package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.repository.QueryRepository;

import java.util.Optional;
import java.util.UUID;

interface StudentQueryRepository extends QueryRepository<StudentSnapshot, UUID> {

    Optional<UUID> findStudentIdByEmail(String email);
}
