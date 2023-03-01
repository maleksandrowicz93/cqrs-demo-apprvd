package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.StudentDto
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
    StudentQueryRepository studentQueryRepository
    @Autowired
    StudentWriteRepository studentWriteRepository

    def setup() {
        StudentUtils.cleanRepository(studentQueryRepository, studentWriteRepository)
    }

    def "get all students"() {
        given: "2 students exist in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())
        studentWriteRepository.save(Students.SECOND.studentToAdd())

        expect: "exactly these students should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    def "add new student"() {
        given: "completely new student's data"
        def request = Students.FIRST.addStudentCommand()

        expect: "this student should be successfully added at POST /student"
        def result = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath('$').isNotEmpty())
                .andReturn()

        and: "there should be correctly created Location header"
        def response = result.getResponse()
        def location = response.getHeader(HttpHeaders.LOCATION)
        def body = gson.fromJson(response.getContentAsString(), StudentDto)
        location.contains("student/" + body.id())
    }

    def "should not add student when already exists"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "his data is ready to be added second time"
        def request = Students.FIRST.addStudentCommand()

        expect: "this student should not be added again at POST /student"
        def result = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath('$').doesNotExist())
                .andReturn()

        and: "there should be correctly created Location header"
        def response = result.getResponse()
        def location = response.getHeader(HttpHeaders.LOCATION)
        def id = studentQueryRepository.findStudentIdByEmail(student.email())
        location.contains("student/" + id.get())
    }

    def "should not add new student when invalid credentials"() {
        given: "completely new student's data"
        def request = Students.FIRST.addStudentCommand().toBuilder()
                .email(null)
                .password(null)
                .build()

        expect: "this student should not be be added at POST /student"
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "get student"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        expect: "this student should be retrieved from GET /student/{id}"
        mockMvc.perform(get("/student/" + student.id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    def "should not get student when not exist"() {
        expect: "for cleared db, a student should not be retrieved from GET /student/{id}"
        mockMvc.perform(get("/student/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "edit student"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "new data for this student update is prepared"
        def request = Students.SECOND.addStudentCommand()

        expect: "this student's data should be edited at PUT /student/{id}"
        mockMvc.perform(put("/student/" + student.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    def "should not edit student when not exist"() {
        given: "a student update data is ready"
        def request = Students.SECOND.addStudentCommand()

        expect: "for cleared db, a student's data should not be edited at PUT /student/{id}"
        mockMvc.perform(put("/student/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "should not edit student when invalid credentials"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        and: "new data with invalid credentials for this student is prepared"
        def request = Students.SECOND.addStudentCommand().toBuilder()
                .email(null)
                .password(null)
                .build()

        expect: "this student should not be be added at POST /student"
        mockMvc.perform(put("/student/" + student.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "update password"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        expect: "this student should have updated password at PATCH /student/{id}"
        mockMvc.perform(put("/student/" + student.id() + "/password")
                .contentType(MediaType.TEXT_PLAIN)
                .content(Students.SECOND.password))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    def "should not update password when student not exist"() {
        expect: "for cleared db, a student's password should not be updated at PATCH /student/{id}"
        mockMvc.perform(put("/student/" + UUID.randomUUID() + "/password")
                .contentType(MediaType.TEXT_PLAIN)
                .content(Students.SECOND.password))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "should not update password when invalid"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        expect: "his password should not be updated at PATCH /student/{id}"
        mockMvc.perform(put("/student/" + student.id() + "/password")
                .contentType(MediaType.TEXT_PLAIN)
                .content(" "))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "delete student"() {
        given: "a student exists in db"
        def student = studentWriteRepository.save(Students.FIRST.studentToAdd())

        expect: "this student can be deleted at DELETE /student/{id}"
        mockMvc.perform(delete("/student/" + student.id()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "deleting not existing student should return success status"() {
        expect: "for cleared db, status of student deletion at DELETE /student/{id} should be 204"
        mockMvc.perform(delete("/student/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath('$').doesNotExist())
    }
}
