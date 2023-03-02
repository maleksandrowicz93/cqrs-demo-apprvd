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
    StudentServicesFactory studentServicesFactory() {
        return new StudentServicesFactory(studentWriteRepository, studentQueryRepository,
                studentMapper, securityService);
    }

    @Bean
    StudentQueriesDispatcher studentQueriesDispatcher(StudentServicesFactory factory) {
        return factory.studentQueriesDispatcher();
    }

    @Bean
    StudentFacade studentFacade(
            StudentServicesFactory factory,
            AddStudentCommandHandler addStudentCommandHandler,
            EditStudentDataCommandHandler editStudentDataCommandHandler,
            UpdatePasswordCommandHandler updatePasswordCommandHandler,
            DeleteStudentCommandHandler deleteStudentCommandHandler) {
        return factory.studentFacade(addStudentCommandHandler,
                editStudentDataCommandHandler,
                updatePasswordCommandHandler,
                deleteStudentCommandHandler);
    }

    @Bean
    AddStudentCommandHandler addStudentCommandHandler(
            StudentServicesFactory factory,
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return factory.addStudentCommandHandler(studentDataApiResultFactory);
    }

    @Bean
    ApiResultFactory<StudentData> studentDataApiResultFactory(StudentServicesFactory factory) {
        return factory.studentDataApiResultFactory();
    }

    @Bean
    EditStudentDataCommandHandler editStudentDataCommandHandler(
            StudentServicesFactory factory,
            ApiResultFactory<StudentData> studentDataApiResultFactory) {
        return factory.editStudentDataCommandHandler(studentDataApiResultFactory);
    }

    @Bean
    UpdatePasswordCommandHandler updatePasswordCommandHandler(
            StudentServicesFactory factory,
            ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory) {
        return factory.updatePasswordCommandHandler(studentIdentificationApiResultFactory);
    }

    @Bean
    ApiResultFactory<StudentIdentification> studentIdentificationApiResultFactory(StudentServicesFactory factory) {
        return factory.studentIdentificationApiResultFactory();
    }

    @Bean
    DeleteStudentCommandHandler deleteStudentCommandHandler(StudentServicesFactory factory) {
        return factory.deleteStudentCommandHandler();
    }
}
