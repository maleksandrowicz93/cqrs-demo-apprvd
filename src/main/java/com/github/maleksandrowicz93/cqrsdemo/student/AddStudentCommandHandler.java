package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class AddStudentCommandHandler {

    private final StudentRepository studentRepository;

//    public AddStudentCommandHandler(StudentRepository studentRepository) {
//        this.studentRepository = studentRepository;
//        studentRepository.save(Student.builder()
//                .email("email")
//                .password("123")
//                .build());
//        System.out.println();
//    }

    StudentDto handle(AddStudentCommand command) {
        return StudentDto.builder().build();
    }
}
