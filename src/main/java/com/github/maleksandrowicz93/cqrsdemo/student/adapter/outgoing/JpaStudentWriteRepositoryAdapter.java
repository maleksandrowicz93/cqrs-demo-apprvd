package com.github.maleksandrowicz93.cqrsdemo.student.adapter.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentWriteRepository;
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
    public Student save(Student student) {
        StudentEntity studentEntity = studentEntityMapper.toStudentEntity(student);
        StudentEntity savedStudent = jpaStudentWriteRepository.save(studentEntity);
        return studentEntityMapper.toStudent(savedStudent);
    }

    @Override
    public void deleteById(UUID id) {
        jpaStudentWriteRepository.deleteById(id);
    }
}
