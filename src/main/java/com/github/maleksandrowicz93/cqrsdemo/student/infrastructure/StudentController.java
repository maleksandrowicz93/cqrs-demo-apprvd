package com.github.maleksandrowicz93.cqrsdemo.student.infrastructure;

import com.github.maleksandrowicz93.cqrsdemo.student.StudentQueriesDispatcher;
import com.github.maleksandrowicz93.cqrsdemo.student.StudentWriteFacade;
import com.github.maleksandrowicz93.cqrsdemo.student.api.StudentApi;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.result.CommandHandlerResult;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.result.ResultProperty.CONFLICTED_ID;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentController implements StudentApi {

    StudentQueriesDispatcher studentQueriesDispatcher;
    StudentWriteFacade studentWriteFacade;
    Environment environment;

    @Override
    public ResponseEntity<List<StudentIdentification>> findAllStudents(Integer page, Integer size) {
        List<StudentIdentification> students = studentQueriesDispatcher.findAllStudents(page, size);
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        var result = studentWriteFacade.addStudent(saveStudentRequest);
        return result.value()
                .map(student -> ResponseEntity
                        .created(URI.create(getLocation(student.id())))
                        .body(student))
                .orElseGet(() -> buildErrorResponseFrom(result));
    }

    private String getLocation(UUID id) {
        return getLocation(id.toString());
    }

    private String getLocation(String id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .port(environment.getProperty("server.port"))
                .path("/" + id)
                .toUriString();
    }

    private <T> ResponseEntity<T> buildErrorResponseFrom(CommandHandlerResult<T> result) {
        return (switch (result.code()) {
            case INVALID_CREDENTIALS -> ResponseEntity.badRequest();
            case STUDENT_NOT_FOUND -> ResponseEntity.notFound();
            case STUDENT_ALREADY_EXISTS -> ResponseEntity
                    .status(CONFLICT)
                    .header(LOCATION, getLocation(result.property(CONFLICTED_ID)));
            default -> ResponseEntity.internalServerError();
        }).build();
    }

    @Override
    public ResponseEntity<StudentDto> findStudent(UUID id) {
        return studentQueriesDispatcher.findStudentById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<StudentDto> editStudent(UUID id, SaveStudentRequest saveStudentRequest) {
        var result = studentWriteFacade.editStudentData(id, saveStudentRequest);
        return result.value()
                .map(ResponseEntity::ok)
                .orElseGet(() -> buildErrorResponseFrom(result));
    }

    @Override
    public ResponseEntity<StudentIdentification> updatePassword(UUID id, String body) {
        var result = studentWriteFacade.updatePassword(id, body);
        return result.value()
                .map(ResponseEntity::ok)
                .orElseGet(() -> buildErrorResponseFrom(result));
    }

    @Override
    public ResponseEntity<Void> deleteStudent(UUID id) {
        studentWriteFacade.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}