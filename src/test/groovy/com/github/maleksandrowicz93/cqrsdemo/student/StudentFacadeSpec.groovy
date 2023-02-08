package com.github.maleksandrowicz93.cqrsdemo.student


import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.student.exception.InvalidCredentialsException
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentAlreadyExistsException
import com.github.maleksandrowicz93.cqrsdemo.student.exception.StudentNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
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
        studentRepository.deleteAll()
    }

    def "get all students"() {
        given: "2 students exist in db"
        def student1 = studentRepository.save(StudentUtils.studentToAdd())
        def student2 = studentRepository.save(StudentUtils.alternativeStudentToAdd())
        def expectedStudents = List.of(StudentUtils.studentDto(student1.id),
                StudentUtils.alternativeStudentDto(student2.id))

        when: "user tries to retrieve them"
        def students = facade.getAllStudents()

        then: "gets exactly these students and no more"
        students.size() == 2
        students.eachWithIndex { StudentDto student, int i -> student == expectedStudents.get(i) }
    }

    def "get empty student list when no student exists"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        expect: "list of students is empty"
        facade.getAllStudents().size() == 0
    }

    def "add new student"() {
        when: "user tries to add a new student"
        def student = facade.addStudent(StudentUtils.addStudentCommand())

        then: "this student should be successfully added"
        def expectedStudent = StudentUtils.studentDto(student.id)
        student == expectedStudent
    }

    def "should not add student when already exists"() {
        given: "student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to add this student"
        facade.addStudent(StudentUtils.addStudentCommand())

        then: "StudentAlreadyExistsException is thrown"
        thrown(StudentAlreadyExistsException)
    }

    def "should not add student when no email"() {
        given: "a new student data with no email"
        def command = StudentUtils.addStudentCommand().email(null)

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty email"() {
        given: "a new student data with empty email"
        def command = StudentUtils.addStudentCommand().email("")

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when no password"() {
        given: "a new student data with no password"
        def command = StudentUtils.addStudentCommand().password(null)

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not add student when empty password"() {
        given: "a new student data with empty password"
        def command = StudentUtils.addStudentCommand().password("")

        when: "user tries to add this student"
        facade.addStudent(command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "get student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())
        def id = studentEntity.id
        def expectedStudent = StudentUtils.studentDto(id)

        when: "user tries to retrieve student's data by student's id"
        def student = facade.getStudent(id)

        then: "gets his correct data"
        student == expectedStudent
    }

    def "should not get student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        when: "user tries to retrieve a student"
        facade.getStudent(1)

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }

    def "edit student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())
        def id = studentEntity.id
        def expectedStudent = StudentUtils.alternativeStudentDto(id)

        when: "user tries to edit student's data"
        def student = facade.editStudentData(id, StudentUtils.editStudentDataCommand())

        then: "data is successfully edited"
        student == expectedStudent
    }

    def "should not edit student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        when: "user tries to edit a student"
        facade.editStudentData(1, StudentUtils.editStudentDataCommand())

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }

    def "should not edit student when no email"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        and: "user fill data to update with no email"
        def command = StudentUtils.editStudentDataCommand().email(null)

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id, command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not edit student when empty email"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        and: "user fill data to update with empty email"
        def command = StudentUtils.editStudentDataCommand().email("")

        when: "user tries to edit student's data"
        facade.editStudentData(studentEntity.id, command)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "update password"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to update student's password"
        def updated = facade.updatePassword(studentEntity.id, StudentUtils.PASSWORD)

        then: "password is successfully updated"
        updated
    }

    def "should not update password when no password"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(1, null)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when empty password"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to update a student's password"
        facade.updatePassword(1, "")

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)
    }

    def "should not update password when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        when: "user tries to update a student's password"
        facade.updatePassword(1, "xyz")

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }

    def "delete student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to delete student's account"
        def deleted = facade.deleteStudent(studentEntity.id)

        then: "account is successfully deleted"
        deleted
    }

    def "should not delete student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        when: "user tries to delete a student"
        facade.deleteStudent(1)

        then: "StudentNotFoundException is thrown"
        thrown(StudentNotFoundException)
    }
}
