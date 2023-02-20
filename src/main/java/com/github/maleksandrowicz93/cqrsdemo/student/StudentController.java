package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.StudentApi;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentController implements StudentApi {

    StudentQueryFacade studentQueryFacade;
    StudentWriteFacade studentWriteFacade;

    @Override
    public ResponseEntity<List<StudentIdentification>> findAllStudents(Integer page, Integer size) {
        List<StudentIdentification> students = studentQueryFacade.getAllStudents(page, size);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        return studentWriteFacade.addStudent(saveStudentRequest)
                .map(student -> {
                    String location = getLocation(student.id());
                    return ResponseEntity
                            .created(URI.create(location))
                            .body(student);
                })
                .orElseGet(() -> {
                    var id = studentQueryFacade.findStudentIdByEmail(saveStudentRequest.email());
                    return ResponseEntity
                            .status(HttpStatus.CONFLICT)
                            .header(HttpHeaders.LOCATION, getLocation(id))
                            .build();
                });
    }

    private String getLocation(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/" + id.toString())
                .toUriString();
    }

    @Override
    public ResponseEntity<StudentDto> findStudent(UUID id) {
        return studentQueryFacade.getStudent(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<StudentDto> editStudent(UUID id, SaveStudentRequest saveStudentRequest) {
        return studentWriteFacade.editStudentData(id, saveStudentRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<StudentIdentification> updatePassword(UUID id, String body) {
        return studentWriteFacade.updatePassword(id, body)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteStudent(UUID id) {
        studentWriteFacade.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
