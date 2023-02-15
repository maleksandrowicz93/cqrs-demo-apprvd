package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.WriteRepository;

import java.util.UUID;

interface StudentWriteRepository extends WriteRepository<Student, UUID> {
    boolean existsByEmail(String email);
}
