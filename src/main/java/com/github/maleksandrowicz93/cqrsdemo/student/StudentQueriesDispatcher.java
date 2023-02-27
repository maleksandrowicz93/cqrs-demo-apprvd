package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentMapper;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.StudentQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentQueriesDispatcher {

    StudentQueryRepository studentQueryRepository;
    StudentMapper studentMapper;

    public List<StudentIdentification> findAllStudents(int page, int size) {
        return studentQueryRepository.findAll(getPageRequest(page, size))
                .getContent().stream()
                .map(studentMapper::toStudentIdentification)
                .toList();
    }

    private PageRequest getPageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.by(
                Sort.Order.asc("lastName"),
                Sort.Order.asc("firstName"),
                Sort.Order.asc("email")
        ));
    }

    public Optional<StudentData> findStudentById(UUID studentId) {
        return studentQueryRepository.findById(studentId)
                .map(studentMapper::toStudentData);
    }
}
