package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.api.StudentApi;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException;
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
class StudentController implements StudentApi {

    private final StudentFacade studentFacade;

    @Override
    public ResponseEntity<List<StudentDto>> findAllStudents() {
        List<StudentDto> students = studentFacade.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @Override
    public ResponseEntity<StudentDto> addStudent(AddStudentCommand addStudentCommand)
            throws InvalidCredentialsException, StudentAlreadyExistsException {
        StudentDto student = studentFacade.addStudent(addStudentCommand);
        return ResponseEntity.ok(student);
    }

    @Override
    public ResponseEntity<StudentDto> findStudent(Long id) throws StudentNotFoundException {
        StudentDto student = studentFacade.getStudent(id);
        return ResponseEntity.ok(student);
    }

    @Override
    public ResponseEntity<StudentDto> editStudent(Long id, EditStudentDataCommand editStudentDataCommand)
            throws InvalidCredentialsException, StudentNotFoundException {
        StudentDto student = studentFacade.editStudentData(id, editStudentDataCommand);
        return ResponseEntity.ok(student);
    }

    @Override
    public ResponseEntity<Boolean> updatePassword(Long id, String body)
            throws InvalidCredentialsException, StudentNotFoundException {
        boolean updated = studentFacade.updatePassword(id, body);
        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Boolean> deleteStudent(Long id) throws StudentNotFoundException {
        boolean deleted = studentFacade.deleteStudent(id);
        return ResponseEntity.ok(deleted);
    }
}
