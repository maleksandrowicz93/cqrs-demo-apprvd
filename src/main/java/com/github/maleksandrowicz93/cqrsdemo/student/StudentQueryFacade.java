package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class StudentQueryFacade {

    StudentQueryHandler studentQueryHandler;

    public List<StudentIdentification> getAllStudents(int page, int size) {
        return studentQueryHandler.findAllStudents(page, size);
    }

    public Optional<StudentDto> getStudent(UUID studentId) {
        return studentQueryHandler.findStudentById(studentId);
    }

    public UUID findStudentIdByEmail(String email) {
        return studentQueryHandler.findStudentIdByEmail(email);
    }
}
