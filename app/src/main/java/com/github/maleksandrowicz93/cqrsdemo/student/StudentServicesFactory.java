package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentServicesFactory {

    StudentWriteRepository studentWriteRepository;
    StudentQueryRepository studentQueryRepository;
    StudentMapper studentMapper;
    SecurityService securityService;
    ApiResultFactory<StudentData> studentDataApiResultFactory = studentDataApiResultFactory();
    ApiResultFactory<StudentIdentification> studentIdApiResultFactory = studentIdentificationApiResultFactory();

    ApiResultFactory<StudentData> studentDataApiResultFactory() {
        return new StudentDataApiResultFactory();
    }

    ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory() {
        return new StudentIdentificationApiResultFactory();
    }

    StudentQueriesDispatcher studentQueriesDispatcher() {
        return new StudentQueriesDispatcher(studentQueryRepository, studentMapper);
    }

    StudentFacade studentFacade() {
        return studentFacade(addStudentCommandHandler(),
                editStudentDataCommandHandler(),
                updatePasswordCommandHandler(),
                deleteStudentCommandHandler());
    }

    StudentFacade studentFacade(
            AddStudentCommandHandler addStudentCommandHandler,
            EditStudentDataCommandHandler editStudentDataCommandHandler,
            UpdatePasswordCommandHandler updatePasswordCommandHandler,
            DeleteStudentCommandHandler deleteStudentCommandHandler) {
        return new StudentFacade(addStudentCommandHandler,
                editStudentDataCommandHandler,
                updatePasswordCommandHandler,
                deleteStudentCommandHandler);
    }

    AddStudentCommandHandler addStudentCommandHandler() {
        return addStudentCommandHandler(studentDataApiResultFactory);
    }

    EditStudentDataCommandHandler editStudentDataCommandHandler() {
        return editStudentDataCommandHandler(studentDataApiResultFactory);
    }

    UpdatePasswordCommandHandler updatePasswordCommandHandler() {
        return updatePasswordCommandHandler(studentIdApiResultFactory);
    }

    DeleteStudentCommandHandler deleteStudentCommandHandler() {
        return new DeleteStudentCommandHandler(studentQueryRepository, studentWriteRepository);
    }

    AddStudentCommandHandler addStudentCommandHandler(
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return new AddStudentCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentDataApiResultFactory,
                securityService);
    }

    EditStudentDataCommandHandler editStudentDataCommandHandler(
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return new EditStudentDataCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentDataApiResultFactory,
                securityService);
    }

    UpdatePasswordCommandHandler updatePasswordCommandHandler(
            ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory) {
        return new UpdatePasswordCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentIdentificationApiResultFactory,
                securityService);
    }
}
