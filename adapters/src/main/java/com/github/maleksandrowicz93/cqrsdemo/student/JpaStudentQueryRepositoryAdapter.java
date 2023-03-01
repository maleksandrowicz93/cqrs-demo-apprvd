package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
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
    public List<Student> findAll(int page, int size) {
        var pageRequest = getPageRequest(page, size);
        return jpaStudentQueryRepository.findAll(pageRequest)
                .map(studentEntityMapper::toStudent)
                .getContent();
    }

    private PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("lastName"),
                Sort.Order.asc("firstName"),
                Sort.Order.asc("email")
        ));
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
