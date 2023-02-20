package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.QueryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

interface StudentQueryRepository extends QueryRepository<Student, UUID> {

    @Query("select s.id from Student s where s.email = :email")
    Optional<UUID> findStudentIdByEmail(@Param("email") String email);
}
