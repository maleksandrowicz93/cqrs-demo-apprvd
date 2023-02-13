package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.BaseRepository;

import java.util.UUID;

interface StudentRepository extends BaseRepository<Student, UUID> {

    boolean existsByEmail(String email);
}
