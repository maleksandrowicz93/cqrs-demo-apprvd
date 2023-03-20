package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.repository.WriteRepository;

import java.util.UUID;

interface StudentWriteRepository extends WriteRepository<StudentSnapshot, UUID> {
}
