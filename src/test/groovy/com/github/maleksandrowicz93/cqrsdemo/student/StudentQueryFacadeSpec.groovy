package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class StudentQueryFacadeSpec extends Specification {

    @Autowired
    StudentQueryFacade facade
    @Autowired
    StudentQueryRepository studentQueryRepository;
    @Autowired
    StudentWriteRepository studentWriteRepository

    def setup() {
        StudentUtils.cleanRepository(studentQueryRepository, studentWriteRepository)
    }

    def "get all students"() {
        given: "2 students exist in db"
        def firstStudent = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def secondStudent = studentWriteRepository.save(Students.SECOND.studentToAdd())
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

    def "get student"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
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

        then: "no student should be get"
        student.isEmpty()
    }
}
