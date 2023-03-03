package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.TestUtils
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand
import spock.lang.Specification

class StudentStubFactory extends Specification {

    static final INSTANCE = new StudentStubFactory()

    StudentMapper studentMapper() {
        Stub(StudentMapper) {
            def firstStudent = Students.FIRST
            def secondStudent = Students.SECOND
            toStudent(firstStudent.addStudentCommand()) >> firstStudent.studentToAdd()
            toStudent(secondStudent.addStudentCommand()) >> secondStudent.studentToAdd()
            toStudent(_ as EditStudentCommand) >> { EditStudentCommand command -> secondStudent.addedStudent(command.id()) }
            toStudentData(_ as Student) >> { Student student ->
                {
                    def studentFactory = Students.from(student)
                    studentFactory.studentData(student.id())
                }
            }
            toStudentIdentification(_ as Student) >> { Student student ->
                {
                    def studentFactory = Students.from(student)
                    studentFactory.studentIdentification(student.id())
                }
            }
        }
    }

    SecurityService securityService() {
        Stub(SecurityService) {
            encodePassword(_ as String) >> { String password -> TestUtils.encodePassword(password) }
        }
    }
}
