package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@SpringBootTest
@ContextConfiguration
class StudentQueriesDispatcherSpec extends Specification {

    @Autowired
    StudentQueriesDispatcher dispatcher
    @Autowired
    StudentQueryRepository studentQueryRepository
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
        def resultPage = dispatcher.findAllStudents(0, 10)

        then: "gets exactly these students and no more"
        def students = resultPage.content()
        students.size() == 2
        students.eachWithIndex { StudentIdentification student, int i -> student == expectedStudents.get(i) }
    }

    def "get students number limited to page size"() {
        given: "2 students exist in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())
        studentWriteRepository.save(Students.SECOND.studentToAdd())

        and: "size of page is equal to 1"
        int size = 1

        expect: "exactly this number of students should be retrieved"
        dispatcher.findAllStudents(0, 1).content().size() == size
    }

    def "get student"() {
        given: "a student exists in db"
        def studentEntity = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def id = studentEntity.id()
        def expectedStudent = Students.FIRST.studentData(id)

        when: "user tries to retrieve student's data by student's id"
        def student = dispatcher.findStudentById(id)

        then: "gets his correct data"
        student.get() == expectedStudent
    }

    def "should not get student when not exist"() {
        when: "user tries to retrieve a student from empty db"
        def student = dispatcher.findStudentById(UUID.randomUUID())

        then: "no student should be get"
        student.isEmpty()
    }
}
