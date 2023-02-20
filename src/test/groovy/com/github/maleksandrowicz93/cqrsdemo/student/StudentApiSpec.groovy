package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
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
        def firstStudent = studentWriteRepository.save(Students.FIRST.studentToAdd())
        def secondStudent = studentWriteRepository.save(Students.SECOND.studentToAdd())
        def sortedStudents = List.of(firstStudent, secondStudent).stream()
                .sorted(Comparator.comparing((Student s) -> s.lastName()))
                .toList()

        expect: "exactly these students should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$', hasSize(2)))
                .andExpect(jsonPath('$[0].id').value(sortedStudents.get(0).id().toString()))
                .andExpect(jsonPath('$[1].id').value(sortedStudents.get(1).id().toString()))
                .andExpect(jsonPath('$[0].email').value(sortedStudents.get(0).email()))
                .andExpect(jsonPath('$[1].email').value(sortedStudents.get(1).email()))
    }

    def "get students number limited to page size"() {
        given: "2 students exist in db"
        studentWriteRepository.save(Students.FIRST.studentToAdd())
        studentWriteRepository.save(Students.SECOND.studentToAdd())

        and: "size of page is equal to 1"
        int size = 1

        expect: "exactly this number of students should be retrieved from GET /student"
        mockMvc.perform(get("/student?page=0&size=" + size))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$', hasSize(size)))
    }

    def "get empty student list when no student exists"() {
        expect: "for cleared db, empty students' list should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$', hasSize(0)))
    }

    def "add new student"() {
        given: "completely new student's data"
        def request = Students.FIRST.saveStudentRequest()

        expect: "this student should be successfully added at POST /student"
        def result = mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('$.id').isString())
                .andExpect(jsonPath('$.email').value(request.email()))
                .andExpect(jsonPath('$.firstName').value(request.firstName()))
                .andExpect(jsonPath('$.lastName').value(request.lastName()))
                .andExpect(jsonPath('$.birthDate').value(request.birthDate().toString()))
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
        def request = Students.FIRST.saveStudentRequest()

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
        def request = Students.FIRST.saveStudentRequest().toBuilder()
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
                .andExpect(jsonPath('$.id').value(student.id().toString()))
                .andExpect(jsonPath('$.email').value(student.email()))
                .andExpect(jsonPath('$.firstName').value(student.firstName()))
                .andExpect(jsonPath('$.lastName').value(student.lastName()))
                .andExpect(jsonPath('$.birthDate').value(student.birthDate().toString()))
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
        def request = Students.SECOND.saveStudentRequest()

        expect: "this student's data should be edited at PUT /student/{id}"
        mockMvc.perform(put("/student/" + student.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('$.id').value(student.id().toString()))
                .andExpect(jsonPath('$.email').value(request.email()))
                .andExpect(jsonPath('$.firstName').value(request.firstName()))
                .andExpect(jsonPath('$.lastName').value(request.lastName()))
                .andExpect(jsonPath('$.birthDate').value(request.birthDate().toString()))
    }

    def "should not edit student when not exist"() {
        given: "a student update data is ready"
        def request = Students.SECOND.saveStudentRequest()

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
        def request = Students.SECOND.saveStudentRequest().toBuilder()
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(Students.SECOND.password))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('$.id').value(student.id().toString()))
                .andExpect(jsonPath('$.email').value(student.email()))
    }

    def "should not update password when student not exist"() {
        expect: "for cleared db, a student's password should not be updated at PATCH /student/{id}"
        mockMvc.perform(put("/student/" + UUID.randomUUID() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
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
                .contentType(MediaType.APPLICATION_JSON)
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
