package com.github.maleksandrowicz93.cqrsdemo.student.adapters.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class JpaStudentQueryRepositoryAdapter implements StudentQueryRepository {

    JpaStudentQueryRepository jpaStudentQueryRepository;
    StudentEntityMapper studentEntityMapper;

    @Override
    public boolean existsById(UUID id) {
        return jpaStudentQueryRepository.existsById(id);
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        Page<StudentEntity> entities = jpaStudentQueryRepository.findAll(pageable);
        return entities.map(studentEntityMapper::toStudent);
    }

    @Override
    public Optional<Student> findById(UUID id) {
        return jpaStudentQueryRepository.findById(id)
                .map(studentEntityMapper::toStudent);
    }

    @Override
    public Optional<UUID> findStudentIdByEmail(String email) {
        return jpaStudentQueryRepository.findStudentIdByEmail(email);
    }
}
