package com.github.maleksandrowicz93.cqrsdemo.student


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
        def result = facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "this student should be successfully added"
        def student = result.value()
        student.isPresent()
        def expectedStudent = Students.FIRST.studentDto(student.get().id())
        student.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not add student when already exists"() {
        given: "student exists in db"
        def existingStudent = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to add this student"
        def result = facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_ALREADY_EXISTS
        result.property(ResultProperty.CONFLICTED_ID) == existingStudent.id().toString()
    }

    def "should not add student when no email"() {
        given: "a new student data with no email"
        def request = Students.FIRST.saveStudentRequest().toBuilder()
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
        def request = Students.FIRST.saveStudentRequest().toBuilder()
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
        def request = Students.FIRST.saveStudentRequest().toBuilder()
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
        def request = Students.FIRST.saveStudentRequest().toBuilder()
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
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()
        def expectedStudent = Students.SECOND.studentDto(id)

        when: "user tries to edit student's data"
        def result = facade.editStudentData(id, Students.SECOND.saveStudentRequest())

        then: "data is successfully edited"
        def student = result.value()
        student.isPresent()
        student.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not edit student when not exist"() {
        when: "user tries to edit a student in empty db"
        def result = facade.editStudentData(UUID.randomUUID(), Students.FIRST.saveStudentRequest())

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_NOT_FOUND
    }

    def "should not edit student when no email"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no email"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .email(null)
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(studentEntity.id(), request)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when empty email"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty email"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .email(" ")
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(studentEntity.id(), request)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when no password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with no password"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .password(null)
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(studentEntity.id(), request)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not edit student when empty password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "user fill data to update with empty password"
        def request = Students.SECOND.saveStudentRequest().toBuilder()
                .password(" ")
                .build()

        when: "user tries to edit student's data"
        def result = facade.editStudentData(studentEntity.id(), request)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "update password"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def expectedStudent = Students.FIRST.studentIdentification(studentEntity.id())

        and: "there is a new password to update the current one"
        def newPassword = Students.SECOND.password

        when: "user tries to update password"
        def result = facade.updatePassword(studentEntity.id(), newPassword)

        then: "identification of student with updated password is returned"
        def student = result.value()
        student.isPresent()
        student.get() == expectedStudent
        result.code() == ResultCode.OK
    }

    def "should not update password when no password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        def result = facade.updatePassword(student.id(), null)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not update password when empty password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        def result = facade.updatePassword(student.id(), " ")

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS
    }

    def "should not update password when student not exist"() {
        when: "user tries to update a student's password in empty db"
        def result = facade.updatePassword(UUID.randomUUID(), Students.SECOND.password)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.STUDENT_NOT_FOUND
    }

    def "delete student"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to delete delete that student"
        facade.deleteStudent(studentEntity.id())

        then: "his account should be successfully deleted"
        studentQueryRepository.findById(studentEntity.id()).isEmpty()
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
