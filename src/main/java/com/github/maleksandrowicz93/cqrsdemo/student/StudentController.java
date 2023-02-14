package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.StudentApi;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentController implements StudentApi {

    StudentFacade studentFacade;

    @Override
    public ResponseEntity<List<StudentIdentification>> findAllStudents(Integer page, Integer size) {
        List<StudentIdentification> students = studentFacade.getAllStudents(page, size);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<StudentDto> addStudent(SaveStudentRequest saveStudentCommand) {
        StudentDto student = studentFacade.addStudent(saveStudentCommand);
        return ResponseEntity.ok(student);
    }

    @Override
    public ResponseEntity<StudentDto> findStudent(UUID id) {
        return studentFacade.getStudent(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<StudentDto> editStudent(UUID id, SaveStudentRequest saveStudentCommand) {
        StudentDto student = studentFacade.editStudentData(id, saveStudentCommand);
        return ResponseEntity.ok(student);
    }

    @Override
    public ResponseEntity<Void> updatePassword(UUID id, String body) {
        studentFacade.updatePassword(id, body);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteStudent(UUID id) {
        studentFacade.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
