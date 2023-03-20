package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.TestUtils
import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand
import spock.lang.Specification

class StudentStubFactory extends Specification {

    static final INSTANCE = new StudentStubFactory()

    StudentMapper studentMapper() {
        Stub(StudentMapper) {
            toStudent(_ as AddStudentCommand) >> { AddStudentCommand command ->
                return buildMockStudentFrom(command)
            }
            toStudent(_ as EditStudentCommand) >> { EditStudentCommand command ->
                return buildMockStudentFrom(command)
            }
            toStudentData(_ as StudentSnapshot) >> { StudentSnapshot student ->
                {
                    def studentFactory = Students.from(student.email())
                    studentFactory.studentData(student.id())
                }
            }
            toStudentIdentification(_ as StudentSnapshot) >> { StudentSnapshot student ->
                {
                    def studentFactory = Students.from(student.email())
                    studentFactory.studentIdentification(student.id())
                }
            }
        }
    }

    private StudentSnapshot buildMockStudentFrom(AddStudentCommand command) {
        if (command.email() == null) {
            return StudentSnapshot.builder().build()
        }
        if (command.email().isBlank()) {
            return StudentSnapshot.builder()
                    .email(" ")
                    .build()
        }
        def studentFactory = Students.from(command.email())
        def student = studentFactory.studentToAdd()
        if (command.password() == null) {
            return student.toBuilder()
                    .password(null)
                    .build()
        }
        if (command.password().isBlank()) {
            return student.toBuilder()
                    .password(" ")
                    .build()
        }
        return student
    }

    private StudentSnapshot buildMockStudentFrom(EditStudentCommand command) {
        if (command.email() == null) {
            return StudentSnapshot.builder()
                    .id(command.id())
                    .build()
        }
        if (command.email().isBlank()) {
            return StudentSnapshot.builder()
                    .id(command.id())
                    .email(" ")
                    .build()
        }
        def studentFactory = Students.from(command.email())
        def student = studentFactory.addedStudent(command.id())
        if (command.password() == null) {
            return student.toBuilder()
                    .password(null)
                    .build()
        }
        if (command.password().isBlank()) {
            return student.toBuilder()
                    .password(" ")
                    .build()
        }
        return student
    }

    SecurityService securityService() {
        Stub(SecurityService) {
            encodePassword(_ as String) >> { String password -> TestUtils.encodePassword(password) }
        }
    }
}
