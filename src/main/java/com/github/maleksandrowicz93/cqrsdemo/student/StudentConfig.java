package com.github.maleksandrowicz93.cqrsdemo.student;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class StudentConfig {

    @Bean
    StudentFacade studentFacade(
            StudentQueryHandler studentQueryHandler,
            AddStudentCommandHandler addStudentCommandHandler,
            EditStudentDataCommandHandler editStudentDataCommandHandler,
            UpdatePasswordCommandHandler updatePasswordCommandHandler,
            DeleteStudentCommandHandler deleteStudentCommandHandler
    ) {
        return new StudentFacade(studentQueryHandler,
                addStudentCommandHandler,
                editStudentDataCommandHandler,
                updatePasswordCommandHandler,
                deleteStudentCommandHandler);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 11);
    }
}
