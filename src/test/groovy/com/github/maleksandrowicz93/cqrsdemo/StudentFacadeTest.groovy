package com.github.maleksandrowicz93.cqrsdemo

import com.github.maleksandrowicz93.cqrsdemo.user.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.user.EditStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.user.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.user.StudentFacade
import spock.lang.Specification

class StudentFacadeTest extends Specification {

    def facade = new StudentFacade()

    def "get all students"() {
        given: "2 students exist in db"
        facade.addStudent(new AddStudentCommand())
        facade.addStudent(new AddStudentCommand())
        def expectedStudents = List.of(new StudentDto(), new StudentDto())

        when: "user tries to retrieve them"
        def students = facade.getAllStudents()

        then: "gets exactly these students and no more"
        students.size() == 2
        students.eachWithIndex { StudentDto student, int i -> student == expectedStudents.get(i) }
    }

    def "add new student"() {
        given: "there is a completely new student data to be added to db"
        def expectedStudent = new StudentDto()

        when: "user tries to add a new student"
        def student = facade.addStudent(new AddStudentCommand())

        then: "this student should be successfully added"
        student == expectedStudent
    }

    def "get student"() {
        given: "a student exists in db"
        facade.addStudent(new AddStudentCommand())
        def expectedStudent = new StudentDto()

        when: "user tries to retrieve student's data by student's id"
        def student = facade.getStudent("")

        then: "gets his correct data"
        student == expectedStudent
    }

    def "update student"() {
        given: "a student exists in db"
        facade.addStudent(new AddStudentCommand())
        def expectedStudent = new StudentDto()

        when: "user tries to edit student's data"
        def student = facade.editStudentData("", new EditStudentCommand())

        then: "data is successfully edited"
        student == expectedStudent
    }

    def "update password"() {
        given: "a student exists in db"
        facade.addStudent(new AddStudentCommand())

        when: "user tries to update student's password"
        def updated = facade.updatePassword("", "")

        then: "password is successfully updated"
        updated
    }

    def "delete student"() {
        given: "a student exists in db"
        facade.addStudent(new AddStudentCommand())

        when: "user tries to delete student's account"
        def deleted = facade.deleteStudent("")

        then: "account is successfully deleted"
        deleted
    }
}
