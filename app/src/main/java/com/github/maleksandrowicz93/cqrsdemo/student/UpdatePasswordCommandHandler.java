package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.INVALID_CREDENTIALS;
import static com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode.STUDENT_NOT_FOUND;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class UpdatePasswordCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;
    StudentMapper studentMapper;
    ApiResultFactory<StudentIdentification> resultFactory;
    SecurityService securityService;

    ApiResult<StudentIdentification> handle(UpdatePasswordCommand command) {
        var id = command.id();
        var password = command.password();
        if (StringUtils.isBlank(password)) {
            log.error("Password passed by student with id {} should not be blank.", id);
            return resultFactory.create(INVALID_CREDENTIALS);
        }
        return studentQueryRepository.findById(id)
                .map(snapshot -> updatePassword(snapshot, password))
                .orElseGet(() -> resultFactory.create(STUDENT_NOT_FOUND));
    }

    private ApiResult<StudentIdentification> updatePassword(StudentSnapshot snapshot, String password) {
        Student student = Student.fromSnapshot(snapshot);
        String encodedPassword = securityService.encodePassword(password);
        student.updatePassword(encodedPassword);
        StudentSnapshot savedStudent = studentWriteRepository.save(student.createSnapshot());
        StudentIdentification studentIdentification = studentMapper.toStudentIdentification(savedStudent);
        return resultFactory.create(studentIdentification);
    }
}
