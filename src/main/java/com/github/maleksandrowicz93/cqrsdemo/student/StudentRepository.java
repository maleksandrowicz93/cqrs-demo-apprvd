package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.ReadRepository;
import com.github.maleksandrowicz93.cqrsdemo.interfaces.WriteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

interface StudentRepository extends ReadRepository<Student, UUID>, WriteRepository<Student, UUID> {

    boolean existsByEmail(String email);
    @Query("select s.id from Student s where s.email = :email")
    UUID findStudentIdByEmail(@Param("email") String email);
}
