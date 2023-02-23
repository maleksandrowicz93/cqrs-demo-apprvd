package com.github.maleksandrowicz93.cqrsdemo.student.adapters.outgoing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface JpaStudentQueryRepository extends JpaRepository<StudentEntity, UUID> {

    @Query("select s.id from student s where s.email = :email")
    Optional<UUID> findStudentIdByEmail(@Param("email") String email);
}
