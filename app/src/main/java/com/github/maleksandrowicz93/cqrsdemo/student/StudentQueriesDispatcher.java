package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentQueriesDispatcher {

    StudentQueryRepository studentQueryRepository;
    StudentMapper studentMapper;

    public List<StudentIdentification> findAllStudents(int page, int size) {
        return studentQueryRepository.findAll(page, size).stream()
                .map(studentMapper::toStudentIdentification)
                .toList();
    }

    public Optional<StudentData> findStudentById(UUID studentId) {
        return studentQueryRepository.findById(studentId)
                .map(studentMapper::toStudentData);
    }
}
