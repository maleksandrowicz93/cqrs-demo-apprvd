package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.BaseRepository;

interface StudentRepository extends BaseRepository<Student, Long> {

    boolean existsByEmail(String email);
}
