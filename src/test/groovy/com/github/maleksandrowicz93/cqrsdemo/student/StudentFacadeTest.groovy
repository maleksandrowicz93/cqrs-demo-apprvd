package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class StudentFacadeTest extends Specification {

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

    def "add new student"() {
        when: "user tries to add a new student"
        def student = facade.addStudent(StudentUtils.addStudentCommand())

        then: "this student should be successfully added"
        def expectedStudent = StudentUtils.studentDto(student.id)
        student == expectedStudent
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

    def "edit student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())
        def id = studentEntity.id
        def expectedStudent = StudentUtils.studentDto(id)

        when: "user tries to edit student's data"
        def student = facade.editStudentData(id, StudentUtils.editStudentDataCommand())

        then: "data is successfully edited"
        student == expectedStudent
    }

    def "update password"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to update student's password"
        def updated = facade.updatePassword(studentEntity.id, StudentUtils.PASSWORD)

        then: "password is successfully updated"
        updated
    }

    def "delete student"() {
        given: "a student exists in db"
        def studentEntity = studentRepository.save(StudentUtils.studentToAdd())

        when: "user tries to delete student's account"
        def deleted = facade.deleteStudent(studentEntity.id)

        then: "account is successfully deleted"
        deleted
    }
}
