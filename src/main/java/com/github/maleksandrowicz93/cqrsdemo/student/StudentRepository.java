package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.ReadRepository;
import com.github.maleksandrowicz93.cqrsdemo.interfaces.WriteRepository;

import java.util.UUID;

interface StudentRepository extends ReadRepository<Student, UUID>, WriteRepository<Student, UUID> {

    boolean existsByEmail(String email);
}
