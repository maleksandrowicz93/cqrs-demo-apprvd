package com.github.maleksandrowicz93.cqrsdemo.student


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
        def student1 = studentRepository.save(StudentUtils.studentToAdd())
        def student2 = studentRepository.save(StudentUtils.alternativeStudentToAdd())

        expect: "exactly these students should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isArray())
                .andExpect(jsonPath("\$", hasSize(2)))
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

    def "edit student"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        and: "and new data for student update is prepared"
        def command = StudentUtils.editStudentDataCommand()

        expect: "this student should be retrieved from PUT /student/{id}"
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

    def "update password"() {
        given: "a student exists in db"
        def student = studentRepository.save(StudentUtils.studentToAdd())

        expect: "this student should have updated password at PATCH /student/{id}"
        mockMvc.perform(patch("/student/" + student.id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("xyz"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("\$").isNotEmpty())
                .andExpect(jsonPath("\$").isBoolean())
                .andExpect(jsonPath("\$").value(true))
    }

    def "delete password"() {
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
}
