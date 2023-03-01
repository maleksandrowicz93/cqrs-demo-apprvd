package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class StudentConfig {

    StudentWriteRepository studentWriteRepository;
    StudentQueryRepository studentQueryRepository;
    StudentMapper studentMapper;
    SecurityService securityService;

    @Bean
    StudentQueriesDispatcher studentQueriesDispatcher() {
        return new StudentQueriesDispatcher(studentQueryRepository, studentMapper);
    }

    @Bean
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

    @Bean
    AddStudentCommandHandler addStudentCommandHandler(
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return new AddStudentCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentDataApiResultFactory,
                securityService);
    }

    @Bean
    ApiResultFactory<StudentData> studentDataApiResultFactory() {
        return new StudentDataApiResultFactory();
    }

    @Bean
    EditStudentDataCommandHandler editStudentDataCommandHandler(
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return new EditStudentDataCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentDataApiResultFactory,
                securityService);
    }

    @Bean
    UpdatePasswordCommandHandler updatePasswordCommandHandler(
            ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory) {
        return new UpdatePasswordCommandHandler(studentQueryRepository,
                studentWriteRepository,
                studentMapper,
                studentIdentificationApiResultFactory,
                securityService);
    }

    @Bean
    ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory() {
        return new StudentIdentificationApiResultFactory();
    }

    @Bean
    DeleteStudentCommandHandler deleteStudentCommandHandler() {
        return new DeleteStudentCommandHandler(studentQueryRepository, studentWriteRepository);
    }
}
