package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.repository.ResultPage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ResultPage<StudentSnapshot> findAll(int page, int size) {
        var pageRequest = getPageRequest(page, size);
        var studentPage = jpaStudentQueryRepository.findAll(pageRequest)
                .map(studentEntityMapper::toStudent);
        return new ResultPage<>(studentPage.getTotalPages(), studentPage.getContent());
    }

    private PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("lastName"),
                Sort.Order.asc("firstName"),
                Sort.Order.asc("email")
        ));
    }

    @Override
    public Optional<StudentSnapshot> findById(UUID id) {
        return jpaStudentQueryRepository.findById(id)
                .map(studentEntityMapper::toStudent);
    }

    @Override
    public Optional<UUID> findStudentIdByEmail(String email) {
        return jpaStudentQueryRepository.findStudentIdByEmail(email);
    }
}
