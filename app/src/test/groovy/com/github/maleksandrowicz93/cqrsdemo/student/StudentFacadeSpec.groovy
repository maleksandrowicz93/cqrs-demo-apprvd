package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty
import spock.lang.Specification
import spock.lang.Unroll

class StudentFacadeSpec extends Specification {

    InMemoryStudentRepository inMemoryStudentRepository = new InMemoryStudentRepository()
    StudentQueryRepository studentQueryRepository = inMemoryStudentRepository
    StudentWriteRepository studentWriteRepository = inMemoryStudentRepository
    StudentMapper studentMapper = StudentStubFactory.INSTANCE.studentMapper()
    SecurityService securityService = StudentStubFactory.INSTANCE.securityService()
    StudentServicesFactory factory = new StudentServicesFactory(inMemoryStudentRepository, inMemoryStudentRepository,
            studentMapper, securityService)
    StudentFacade facade = this.factory.studentFacade()

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

    @Unroll("email: #command.email(), password: #command.password()")
    def "should not add student when incorrect credentials"() {
        when: "user tries to add student"
        def result = facade.addStudent(command)

        then: "no student should be created"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS

        where: "student's credentials are invalid"
        command << [
                addStudentCommandBuilder().email(null).build(),
                addStudentCommandBuilder().email(" ").build(),
                addStudentCommandBuilder().password(null).build(),
                addStudentCommandBuilder().password(" ").build()
        ]
    }

    private def addStudentCommandBuilder() {
        Students.FIRST.addStudentCommand().toBuilder()
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

    @Unroll("email: #command.email(), password: #command.password()")
    def "should not edit student when incorrect credentials"() {
        given: "a student exists in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to edit student's data"
        def result = facade.editStudentData(command)

        then: "no updated data should be get"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS

        where: "student's credentials are invalid"
        command << [
                editStudentCommandBuilder().email(null).build(),
                editStudentCommandBuilder().email(" ").build(),
                editStudentCommandBuilder().password(null).build(),
                editStudentCommandBuilder().password(" ").build()
        ]
    }

    private def editStudentCommandBuilder() {
        Students.FIRST.editStudentCommand(UUID.randomUUID()).toBuilder()
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

    @Unroll("password: #command.password()")
    def "should not update password when incorrect password"() {
        given: "a student exists in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())

        when: "user tries to update a student's password"
        def result = facade.updatePassword(command)

        then: "password should not be updated"
        result.value().isEmpty()
        result.code() == ResultCode.INVALID_CREDENTIALS

        where: "new password is incorrect"
        command << [
                updatePasswordCommandBuilder().password(null).build(),
                updatePasswordCommandBuilder().password(" ").build()
        ]
    }

    private def updatePasswordCommandBuilder() {
        Students.FIRST.updatePasswordCommand(UUID.randomUUID()).toBuilder()
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
