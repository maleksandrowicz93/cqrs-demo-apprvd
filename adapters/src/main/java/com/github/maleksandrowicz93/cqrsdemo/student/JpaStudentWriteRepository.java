package com.github.maleksandrowicz93.cqrsdemo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface JpaStudentWriteRepository extends JpaRepository<StudentEntity, UUID> {
}
