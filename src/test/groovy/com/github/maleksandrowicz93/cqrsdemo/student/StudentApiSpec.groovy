package com.github.maleksandrowicz93.cqrsdemo.student


import com.github.maleksandrowicz93.cqrsdemo.student.exception.ErrorMessage
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@ContextConfiguration(classes = IntegrationTestConfig.class)
@AutoConfigureMockMvc
class StudentApiSpec extends Specification {

    @Autowired
    MockMvc mockMvc
    @Autowired
    Gson gson
    @Autowired
    StudentRepository studentRepository

    def setup() {
        studentRepository.deleteAll()
    }

    def "get all students"() {
        given: "2 students exist in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())
        def student1 = studentRepository.save(StudentUtils.alternativeStudentToAdd())

        expect: "exactly these students should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", hasSize(2)))
                .andExpect(jsonPath("\$[0].id").value(student.id))
                .andExpect(jsonPath("\$[1].id").value(student1.id))
                .andExpect(jsonPath("\$[0].email").value(student.email))
                .andExpect(jsonPath("\$[1].email").value(student1.email))
                .andExpect(jsonPath("\$[0].firstName").value(student.firstName))
                .andExpect(jsonPath("\$[1].firstName").value(student1.firstName))
                .andExpect(jsonPath("\$[0].lastName").value(student.lastName))
                .andExpect(jsonPath("\$[1].lastName").value(student1.lastName))
                .andExpect(jsonPath("\$[0].birthDate").value(student.birthDate.toString()))
                .andExpect(jsonPath("\$[1].birthDate").value(student1.birthDate.toString()))
    }

    def "get empty student list when no student exists"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        expect: "empty list should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", hasSize(0)))
    }

    def "add new student"() {
        given: "completely new student's data"
        def command = StudentUtils.addStudentCommand()

        expect: "this student should be successfully added at POST /student"
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.id").isNumber())
                .andExpect(jsonPath("\$.email").value(command.email))
                .andExpect(jsonPath("\$.firstName").value(command.firstName))
                .andExpect(jsonPath("\$.lastName").value(command.lastName))
                .andExpect(jsonPath("\$.birthDate").value(command.birthDate.toString()))
    }

    def "should not add student when already exists"() {
        given: "a student exists in db"
        studentRepository.save(StudentUtils.studentToAdd())

        and: "his data is ready to be added second time"
        def command = StudentUtils.addStudentCommand()

        expect: "this student should not be added again at POST /student"
        def errorMessage = ErrorMessage.STUDENT_ALREADY_EXISTS
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "should not add new student when invalid credentials"() {
        given: "completely new student's data"
        def command = StudentUtils.addStudentCommand().toBuilder()
                .email(null)
                .password(null)
                .build()

        expect: "this student should not be be added at POST /student"
        def errorMessage = ErrorMessage.INVALID_CREDENTIALS
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "get student"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        expect: "this student should be retrieved from GET /student/{id}"
        mockMvc.perform(get("/student/" + student.id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.id").value(student.id))
                .andExpect(jsonPath("\$.email").value(student.email))
                .andExpect(jsonPath("\$.firstName").value(student.firstName))
                .andExpect(jsonPath("\$.lastName").value(student.lastName))
                .andExpect(jsonPath("\$.birthDate").value(student.birthDate.toString()))
    }

    def "should not get student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        expect: "a student should not be retrieved from GET /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(get("/student/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "edit student"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        and: "new data for this student update is prepared"
        def command = StudentUtils.editStudentDataCommand()

        expect: "this student's data should be edited at PUT /student/{id}"
        mockMvc.perform(put("/student/" + student.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.id").value(student.id))
                .andExpect(jsonPath("\$.email").value(command.email))
                .andExpect(jsonPath("\$.firstName").value(command.firstName))
                .andExpect(jsonPath("\$.lastName").value(command.lastName))
                .andExpect(jsonPath("\$.birthDate").value(command.birthDate.toString()))
    }

    def "should not edit student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        and: "a student update data is ready"
        def command = StudentUtils.editStudentDataCommand()

        expect: "a student's data should not be edited at PUT /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(put("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "should not edit student when invalid email"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        and: "new data for student update is prepared"
        def command = StudentUtils.editStudentDataCommand().toBuilder()
                .email(null)
                .build()

        expect: "this student should not be edited at PUT /student/{id}"
        def errorMessage = ErrorMessage.INVALID_CREDENTIALS
        mockMvc.perform(put("/student/" + student.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "update password"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        expect: "this student should have updated password at PATCH /student/{id}"
        mockMvc.perform(patch("/student/" + student.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(StudentUtils.ALTERNATIVE_PASSWORD))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$").isBoolean())
                .andExpect(jsonPath("\$").value(true))
    }

    def "should not update password when student not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        expect: "a student's password should not be updated at PATCH /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(patch("/student/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(StudentUtils.ALTERNATIVE_PASSWORD))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "should not update password when invalid"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        expect: "his password should not be updated at PATCH /student/{id}"
        def errorMessage = ErrorMessage.INVALID_CREDENTIALS
        mockMvc.perform(patch("/student/" + student.id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(" "))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }

    def "delete student"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        expect: "this student can be deleted at DELETE /student/{id}"
        mockMvc.perform(delete("/student/" + student.id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$").isBoolean())
                .andExpect(jsonPath("\$").value(true))
    }

    def "should not delete student when not exist"() {
        given: "students' db is empty"
        studentRepository.deleteAll()

        expect: "a student should not be deleted at DELETE /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(delete("/student/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$.code").value(errorMessage.name()))
                .andExpect(jsonPath("\$.message").value(errorMessage.message))
    }
}
