package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class StudentWriteFacadeSpec extends Specification {

    @Autowired
    StudentWriteFacade facade
    @Autowired
    StudentQueryRepository studentQueryRepository;
    @Autowired
    StudentWriteRepository studentWriteRepository

    def setup() {
        StudentUtils.cleanRepository(studentQueryRepository, studentWriteRepository)
    }

    def "add new student"() {
        when: "user tries to add a new student"
        def student = facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "this student should be successfully added"
        def expectedStudent = Students.FIRST.studentDto(student.get().id())
        student.get() == expectedStudent
    }

    def "should not add student when already exists"() {
        given: "student exists in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to add this student"
        def student = facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "no student should be created"
        student.isEmpty()
    }

    def "should not add student when no email"() {
        given: "a new student data with no email"
        def request = Students.FIRST.saveStudentRequest().toBuilder()
                .email(null)
                .build()

        when: "user tries to add this student"
        facade.addStudent(request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty email"() {
        given: "a new student data with empty email"
        def request = Students.FIRST.saveStudentRequest().toBuilder()
                .email(" ")
                .build()

        when: "user tries to add this student"
        facade.addStudent(request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when no password"() {
        given: "a new student data with no password"
        def request = Students.FIRST.saveStudentRequest().toBuilder()
                .password(null)
                .build()

        when: "user tries to add this student"
        facade.addStudent(request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty password"() {
        given: "a new student data with empty password"
        def request = Students.FIRST.saveStudentRequest().toBuilder()
                .password(" ")
                .build()

        when: "user tries to add this student"
        facade.addStudent(request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "edit student"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()
        def expectedStudent = Students.SECOND.studentDto(id)

        when: "user tries to edit student's data"
        def student = facade.editStudentData(id, Students.SECOND.saveStudentRequest())

        then: "data is successfully edited"
        student.get() == expectedStudent
    }

    def "should not edit student when not exist"() {
        when: "user tries to edit a student in empty db"
        def student = facade.editStudentData(UUID.randomUUID(), Students.FIRST.saveStudentRequest())

        then: "no updated data should be get"
        student.isEmpty()
    }

    def "should not edit student when no email"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no email"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .email(null)
                .build()

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id(), request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not edit student when empty email"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty email"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .email(" ")
                .build()

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id(), request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not edit student when no password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no password"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .password(null)
                .build()

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id(), request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not edit student when empty password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty password"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .password(" ")
                .build()

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id(), request)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "update password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def expectedStudent = Students.FIRST.studentIdentification(studentEntity.id())

        and: "there is a new password to update the current one"
        def newPassword = Students.SECOND.password

        when: "user tries to update password"
        def student = facade.updatePassword(studentEntity.id(), newPassword)

        then: "identification of student with updated password is returned"
        student.get() == expectedStudent
    }

    def "should not update password when no password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(student.id(), null)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when empty password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(student.id(), " ")

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when student not exist"() {
        when: "user tries to update a student's password in empty db"
        def student = facade.updatePassword(UUID.randomUUID(), Students.SECOND.password)

        then: "no updated user is return"
        student.isEmpty()
    }

    def "delete student"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to delete delete that student"
        facade.deleteStudent(studentEntity.id())

        then: "his account should be successfully deleted"
        studentWriteRepository.findById(studentEntity.id()).isEmpty()
    }

    def "should not delete any student when the one to be deleted not exist"() {
        given: "few students exist in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())
        studentWriteRepository.save(Students.SECOND.studentToAdd())

        and: "number of all students is known"
        def pageRequest = StudentUtils.PAGE_REQUEST
        def expectedStudentsNumber = studentQueryRepository.findAll(pageRequest).getTotalElements()

        when: "user tries to delete not existing student"
        facade.deleteStudent(UUID.randomUUID())

        then: "no deletion should be called on db"
        def currentStudentsNumber = studentQueryRepository.findAll(pageRequest).getTotalElements()
        currentStudentsNumber == expectedStudentsNumber
    }
}
