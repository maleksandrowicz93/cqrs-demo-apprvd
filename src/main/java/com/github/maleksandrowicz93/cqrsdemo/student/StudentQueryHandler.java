package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Log4j2
@Component
@RequiredArgsConstructor
class StudentQueryHandler {

    final StudentRepository studentRepository;
    final StudentMapper studentMapper;

    List<StudentIdentification> findAllStudents(int page, int size) {
        return studentRepository.findAll(getPageRequest(page, size))
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

    StudentDto findStudentById(UUID studentId) {
        return studentRepository.findById(studentId)
                .map(studentMapper::toStudentDto)
                .orElseThrow(() -> {
                    log.error("Not found student with id: {}", studentId);
                    throw new StudentNotFoundException();
                });
    }
}
