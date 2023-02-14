package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class StudentFacadeSpec extends Specification {

    @Autowired
    StudentRepository studentRepository
    @Autowired
    StudentFacade facade

    def setup() {
        def pageRequest = PageRequest.of(0, 10)
        def pageNumber = studentRepository.findAll(pageRequest).getTotalPages()
        for (i in 0..<pageNumber) {
            studentRepository.findAll(pageRequest).getContent()
                    .forEach(student -> studentRepository.deleteById(student.id()))
        }
    }

    def "get all students"() {
        given: "2 students exist in db"
        def firstStudent = studentRepository.save(Students.FIRST.studentToAdd())
        def secondStudent = studentRepository.save(Students.SECOND.studentToAdd())
        def expectedStudents = List.of(Students.FIRST.studentIdentification(firstStudent.id()),
                Students.SECOND.studentIdentification(secondStudent.id()))

        when: "user tries to retrieve them"
        def students = facade.getAllStudents(0, 10)

        then: "gets exactly these students and no more"
        students.size() == 2
        students.eachWithIndex { StudentIdentification student, int i -> student == expectedStudents.get(i) }
    }

    def "get empty student list when no student exists"() {
        expect: "list of students retrieved from empty db is empty"
        facade.getAllStudents(0, 10).size() == 0
    }

    def "add new student"() {
        when: "user tries to add a new student"
        def student = facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "this student should be successfully added"
        def expectedStudent = Students.FIRST.studentDto(student.id())
        student == expectedStudent
    }

    def "should not add student when already exists"() {
        given: "student exists in db"
        studentRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to add this student"
        facade.addStudent(Students.FIRST.saveStudentRequest())

        then: "StudentAlreadyExistsException is thrown"
        thrown(StudentAlreadyExistsException)
    }

    def "should not add student when no email"() {
        given: "a new student data with no email"
        def command = Students.FIRST.saveStudentRequest().toBuilder()
                .email(null)
                .build()

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty email"() {
        given: "a new student data with empty email"
        def command = Students.FIRST.saveStudentRequest().toBuilder()
                .email(" ")
                .build()

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when no password"() {
        given: "a new student data with no password"
        def command = Students.FIRST.saveStudentRequest().toBuilder()
                .password(null)
                .build()

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty password"() {
        given: "a new student data with empty password"
        def command = Students.FIRST.saveStudentRequest().toBuilder()
                .password(" ")
                .build()

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "get student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()
        def expectedStudent = Students.FIRST.studentDto(id)

        when: "user tries to retrieve student's data by student's id"
        def student = facade.getStudent(id)

        then: "gets his correct data"
        student.get() == expectedStudent
    }

    def "should not get student when not exist"() {
        when: "user tries to retrieve a student from empty db"
        def student = facade.getStudent(UUID.randomUUID())

        then: "StudentNotFoundException is thrown"
        student.isEmpty()
    }

    def "edit student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()
        def expectedStudent = Students.SECOND.studentDto(id)

        when: "user tries to edit student's data"
        def student = facade.editStudentData(id, Students.SECOND.saveStudentRequest())

        then: "data is successfully edited"
        student == expectedStudent
    }

    def "edit student only with updated data"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()

        and: "there is a new email to update the current one"
        def newEmail = Students.SECOND.email
        def newStudentData = SaveStudentRequest.builder()
                .email(newEmail)
                .build()

        when: "user tries to edit student's data with this email"
        def student = facade.editStudentData(id, newStudentData)

        then: "only student's email should be changed"
        def expectedStudent = Students.FIRST.studentDto(id).toBuilder()
                .email(newEmail)
                .build()
        student == expectedStudent
    }

    def "should not edit student when not exist"() {
        when: "user tries to edit a student in empty db"
        facade.editStudentData(UUID.randomUUID(), Students.FIRST.saveStudentRequest())

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }

    def "update password"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(Students.FIRST.studentToAdd())

        and: "there is a new password to update the current one"
        def newPassword = Students.SECOND.password

        expect: "his password can be successfully updated"
        facade.updatePassword(studentEntity.id(), newPassword)
    }

    def "should not update password when no password"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(student.id(), null)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when empty password"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(student.id(), " ")

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when student not exist"() {
        when: "user tries to update a student's password in empty db"
        facade.updatePassword(UUID.randomUUID(), Students.SECOND.password)

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }

    def "delete student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "his account can be successfully deleted"
        facade.deleteStudent(studentEntity.id())
    }

    def "should not delete student when not exist"() {
        when: "user tries to delete a student from empty db"
        facade.deleteStudent(UUID.randomUUID())

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }
}
