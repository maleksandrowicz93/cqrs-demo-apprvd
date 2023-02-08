package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.interfaces.Converter;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentDataCommand;
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto;

final class StudentConverters {

    static final Converter<Student, StudentDto> STUDENT_TO_STUDENT_DTO = StudentToStudentDto.INSTANCE;
    static final Converter<AddStudentCommand, Student> ADD_STUDENT_COMMAND_TO_STUDENT = AddStudentCommandToStudent.INSTANCE;
    static final Converter<EditStudentDataCommand, Student> EDIT_STUDENT_DATA_COMMAND_TO_STUDENT = EditStudentDataCommandToStudent.INSTANCE;

    private enum StudentToStudentDto implements Converter<Student, StudentDto> {
        INSTANCE {
            @Override
            public StudentDto convert(Student student) {
                return StudentDto.builder()
                        .id(student.getId())
                        .email(student.getEmail())
                        .firstName(student.getFirstName())
                        .lastName(student.getLastName())
                        .birthDate(student.getBirthDate())
                        .build();
            }
        }
    }

    private enum AddStudentCommandToStudent implements Converter<AddStudentCommand, Student> {
        INSTANCE {
            @Override
            public Student convert(AddStudentCommand command) {
                return Student.builder()
                        .email(command.getEmail())
                        .password(command.getPassword())
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .birthDate(command.getBirthDate())
                        .build();
            }
        }
    }

    private enum EditStudentDataCommandToStudent implements Converter<EditStudentDataCommand, Student> {
        INSTANCE {
            @Override
            public Student convert(EditStudentDataCommand command) {
                return Student.builder()
                        .email(command.getEmail())
                        .firstName(command.getFirstName())
                        .lastName(command.getLastName())
                        .birthDate(command.getBirthDate())
                        .build();
            }
        }
    }
}
