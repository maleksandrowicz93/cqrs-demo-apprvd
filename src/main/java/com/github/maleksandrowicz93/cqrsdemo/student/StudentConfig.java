package com.github.maleksandrowicz93.cqrsdemo.student;

import org.springframework.context.annotation.Configuration;

@Configuration
class StudentConfig {

    StudentFacade studentFacade() {
        return new StudentFacade(new StudentQueryHandler(),
                new AddStudentCommandHandler(),
                new EditStudentDataCommandHandler(),
                new UpdatePasswordCommandHandler(),
                new DeleteStudentCommandHandler());
    }
}
