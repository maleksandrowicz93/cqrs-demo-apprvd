package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class JpaStudentWriteRepositoryAdapter implements StudentWriteRepository {

    JpaStudentWriteRepository jpaStudentWriteRepository;
    StudentEntityMapper studentEntityMapper;

    @Override
    public StudentSnapshot save(StudentSnapshot student) {
        var studentEntity = studentEntityMapper.toStudentEntity(student);
        var savedStudent = jpaStudentWriteRepository.save(studentEntity);
        return studentEntityMapper.toStudent(savedStudent);
    }

    @Override
    public void deleteById(UUID id) {
        jpaStudentWriteRepository.deleteById(id);
    }
}
