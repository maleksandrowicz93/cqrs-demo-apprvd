package com.github.maleksandrowicz93.cqrsdemo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaStudentRepository extends JpaRepository<Student, Long>, StudentRepository {
}
