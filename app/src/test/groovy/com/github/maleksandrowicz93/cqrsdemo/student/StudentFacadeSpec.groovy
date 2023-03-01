package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty
import spock.lang.Specification

class StudentFacadeSpec extends Specification {

    StudentFacade facade
    StudentQueryRepository studentQueryRepository
    StudentWriteRepository studentWriteRepository

    def setup() {
        StudentUtils.cleanRepository(studentQueryRepository, studentWriteRepository)
    }

    def "add new student"() {
        when: "user tries to add a new student"
        def result = facade.addStudent(Students.FIRST.addStudentCommand())

        then: "this student should be successfully added"
        def student = result.value()
        student.isPresent()
        def expectedStudent = Students.FIRST.studentData(student.get().id())
        student.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not add student when already exists"() {
        given: "student exists in db"
        def existingStudent = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to add this student"
        def result = facade.addStudent(Students.FIRST.addStudentCommand())

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_ALREADY_EXISTS
        result.property(ResultProperty.CONFLICTED_ID) == existingStudent.id().toString()
    }

    def "should not add student when no email"() {
        given: "a new student data with no email"
        def request = Students.FIRST.addStudentCommand().toBuilder()
                .email(null)
                .build()

        when: "user tries to add this student"
        def result = facade.addStudent(request)

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not add student when empty email"() {
        given: "a new student data with empty email"
        def request = Students.FIRST.addStudentCommand().toBuilder()
                .email(" ")
                .build()

        when: "user tries to add this student"
        def result = facade.addStudent(request)

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not add student when no password"() {
        given: "a new student data with no password"
        def request = Students.FIRST.addStudentCommand().toBuilder()
                .password(null)
                .build()

        when: "user tries to add this student"
        def result = facade.addStudent(request)

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not add student when empty password"() {
        given: "a new student data with empty password"
        def request = Students.FIRST.addStudentCommand().toBuilder()
                .password(" ")
                .build()

        when: "user tries to add this student"
        def result = facade.addStudent(request)

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "edit student"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def id = student.id()
        def expectedStudent = Students.SECOND.studentData(id)

        when: "user tries to edit student's data"
        def result = facade.editStudentData(Students.SECOND.editStudentCommand(id))

        then: "data is successfully edited"
        def studentData = result.value()
        studentData.isPresent()
        studentData.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not edit student when not exist"() {
        given: "there is data of not existing student"
        def command = Students.FIRST.editStudentCommand(UUID.randomUUID())

        when: "user tries to edit a student in empty db"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_NOT_FOUND
    }

    def "should not edit student when no email"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no email"
        def command = Students.SECOND.editStudentCommand(student.id()).toBuilder()
                .email(null)
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when empty email"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty email"
        def command = Students.SECOND.editStudentCommand(student.id()).toBuilder()
                .email(" ")
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when no password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no password"
        def command = Students.SECOND.editStudentCommand(student.id()).toBuilder()
                .password(null)
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when empty password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty password"
        def command = Students.SECOND.editStudentCommand(student.id()).toBuilder()
                .password(" ")
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "update password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def expectedStudent = Students.FIRST.studentIdentification(student.id())

        and: "there is a new password to update the current one"
        def command = Students.SECOND.updatePasswordCommand(student.id())

        when: "user tries to update password"
        def result = facade.updatePassword(command)

        then: "identification of student with updated password is returned"
        def studentIdentification = result.value()
        studentIdentification.isPresent()
        studentIdentification.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not update password when student not exist"() {
        given: "there is data of not existing student"
        def command = Students.SECOND.updatePasswordCommand(UUID.randomUUID())

        when: "user tries to update a student's password in empty db"
        def result = facade.updatePassword(command)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_NOT_FOUND
    }

    def "should not update password when no password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "student fills new password with no text"
        def command = Students.SECOND.updatePasswordCommand(student.id()).toBuilder()
                .password(null)
                .build()

        when: "user tries to update a student's password"
        def result = facade.updatePassword(command)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not update password when empty password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "student fills new password with empty text"
        def command = Students.SECOND.updatePasswordCommand(student.id()).toBuilder()
                .password(" ")
                .build()

        when: "user tries to update a student's password"
        def result = facade.updatePassword(command)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "delete student"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def command = Students.FIRST.deleteStudentCommand(student.id())

        when: "user tries to delete delete that student"
        facade.deleteStudent(command)

        then: "his account should be successfully deleted"
        studentQueryRepository.findById(student.id()).isEmpty()
    }

    def "should not delete any student when the one to be deleted not exist"() {
        given: "few students exist in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())
        studentWriteRepository.save(Students.SECOND.studentToAdd())

        and: "number of all students is known"
        def page = StudentUtils.PAGE
        def size = StudentUtils.SIZE
        def resultPage = studentQueryRepository.findAll(page, size)
        def expectedStudentsNumber = resultPage.content().size()

        when: "user tries to delete not existing student"
        facade.deleteStudent(Students.FIRST.deleteStudentCommand(UUID.randomUUID()))

        then: "no deletion should be called on db"
        def finalResultPage = studentQueryRepository.findAll(page, size)
        def finalStudentsNumber = finalResultPage.content().size()
        finalStudentsNumber == expectedStudentsNumber
    }
}
