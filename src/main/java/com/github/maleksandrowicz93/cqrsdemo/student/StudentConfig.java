package com.github.maleksandrowicz93.cqrsdemo.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class StudentConfig {

    @Bean
    StudentFacade studentFacade(StudentRepository studentRepository) {
        return new StudentFacade(new StudentQueryHandler(studentRepository),
                new AddStudentCommandHandler(studentRepository),
                new EditStudentDataCommandHandler(studentRepository),
                new UpdatePasswordCommandHandler(studentRepository),
                new DeleteStudentCommandHandler(studentRepository));
    }
}
