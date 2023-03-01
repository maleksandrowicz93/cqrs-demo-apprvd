package com.github.maleksandrowicz93.cqrsdemo.student.rest.controller;

import com.github.maleksandrowicz93.cqrsdemo.student.ApiResult;
import com.github.maleksandrowicz93.cqrsdemo.student.StudentFacade;
import com.github.maleksandrowicz93.cqrsdemo.student.StudentQueriesDispatcher;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.api.StudentApi;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.SaveStudentRequest;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentIdDto;
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentPage;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty.CONFLICTED_ID;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentController implements StudentApi {

    StudentQueriesDispatcher studentQueriesDispatcher;
    StudentFacade studentFacade;
    RestModelMapper restModelMapper;
    Environment environment;

    @Override
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<StudentPage> findAllStudents(Integer page, Integer size) {
        var resultPage = studentQueriesDispatcher.findAllStudents(page, size);
        var studentPage = restModelMapper.toStudentPage(resultPage);
        return ResponseEntity.ok(studentPage);
    }

    @Override
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<StudentDto> addStudent(SaveStudentRequest saveStudentRequest) {
        var command = restModelMapper.toAddStudentCommand(saveStudentRequest);
        var result = studentFacade.addStudent(command);
        return result.value()
                .map(restModelMapper::toStudentDto)
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

    private <T, R> ResponseEntity<R> buildErrorResponseFrom(ApiResult<T> result) {
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
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<StudentDto> findStudent(UUID id) {
        return studentQueriesDispatcher.findStudentById(id)
                .map(restModelMapper::toStudentDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<StudentDto> editStudent(UUID id, SaveStudentRequest saveStudentRequest) {
        var command = restModelMapper.toEditStudentCommand(id, saveStudentRequest);
        var result = studentFacade.editStudentData(command);
        return result.value()
                .map(restModelMapper::toStudentDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> buildErrorResponseFrom(result));
    }

    @Override
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<StudentIdDto> updatePassword(UUID id, String body) {
        var command = restModelMapper.toUpdatePasswordCommand(id, body);
        var result = studentFacade.updatePassword(command);
        return result.value()
                .map(restModelMapper::toStudentIdDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> buildErrorResponseFrom(result));
    }

    @Override
    @CrossOrigin(origins = AllowedOrigin.FRONT_END)
    public ResponseEntity<Void> deleteStudent(UUID id) {
        var command = restModelMapper.toDeleteStudentCommand(id);
        studentFacade.deleteStudent(command);
        return ResponseEntity.noContent().build();
    }
}
