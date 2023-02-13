package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.exception.ErrorMessage
import com.github.maleksandrowicz93.cqrsdemo.student.exception.PasswordNotUpdatedException
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.hamcrest.Matchers.hasSize
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
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
    StudentRepository studentRepository

    def setup() {
        studentRepository.findAll()
                .forEach(student -> studentRepository.deleteById(student.id()))
    }

    def "get all students"() {
        given: "2 students exist in db"
        def firstStudent = studentRepository.save(Students.FIRST.studentToAdd())
        def secondStudent = studentRepository.save(Students.SECOND.studentToAdd())
        def sortedStudents = List.of(firstStudent, secondStudent).stream()
                .sorted(Comparator.comparing((Student s) -> s.lastName()))
                .toList()

        expect: "exactly these students should be retrieved from GET /student"
        mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$', hasSize(2)))
                .andExpect(jsonPath('\$[0].id').value(sortedStudents.get(0).id().toString()))
                .andExpect(jsonPath('\$[1].id').value(sortedStudents.get(1).id().toString()))
                .andExpect(jsonPath('\$[0].email').value(sortedStudents.get(0).email()))
                .andExpect(jsonPath('\$[1].email').value(sortedStudents.get(1).email()))
    }

    def "get students number limited to page size"() {
        given: "2 students exist in db"
        studentRepository.save(Students.FIRST.studentToAdd())
        studentRepository.save(Students.SECOND.studentToAdd())

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
        def command = Students.FIRST.saveStudentRequest()

        expect: "this student should be successfully added at POST /student"
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.id').isString())
                .andExpect(jsonPath('\$.email').value(command.email()))
                .andExpect(jsonPath('\$.firstName').value(command.firstName()))
                .andExpect(jsonPath('\$.lastName').value(command.lastName()))
                .andExpect(jsonPath('\$.birthDate').value(command.birthDate().toString()))
    }

    def "should not add student when already exists"() {
        given: "a student exists in db"
        studentRepository.save(Students.FIRST.studentToAdd())

        and: "his data is ready to be added second time"
        def command = Students.FIRST.saveStudentRequest()

        expect: "this student should not be added again at POST /student"
        def errorMessage = ErrorMessage.STUDENT_ALREADY_EXISTS
        mockMvc.perform(post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "should not add new student when invalid credentials"() {
        given: "completely new student's data"
        def command = Students.FIRST.saveStudentRequest().toBuilder()
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
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "get student"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "this student should be retrieved from GET /student/{id}"
        mockMvc.perform(get("/student/" + student.id()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.id').value(student.id().toString()))
                .andExpect(jsonPath('\$.email').value(student.email()))
                .andExpect(jsonPath('\$.firstName').value(student.firstName()))
                .andExpect(jsonPath('\$.lastName').value(student.lastName()))
                .andExpect(jsonPath('\$.birthDate').value(student.birthDate().toString()))
    }

    def "should not get student when not exist"() {
        expect: "for cleared db, a student should not be retrieved from GET /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(get("/student/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "edit student"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        and: "new data for this student update is prepared"
        def command = Students.SECOND.saveStudentRequest()

        expect: "this student's data should be edited at PUT /student/{id}"
        mockMvc.perform(put("/student/" + student.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.id').value(student.id().toString()))
                .andExpect(jsonPath('\$.email').value(command.email()))
                .andExpect(jsonPath('\$.firstName').value(command.firstName()))
                .andExpect(jsonPath('\$.lastName').value(command.lastName()))
                .andExpect(jsonPath('\$.birthDate').value(command.birthDate().toString()))
    }

    def "should not edit student when not exist"() {
        given: "a student update data is ready"
        def command = Students.SECOND.saveStudentRequest()

        expect: "for cleared db, a student's data should not be edited at PUT /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(put("/student/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(command)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "update password"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "this student should have updated password at PATCH /student/{id}"
        mockMvc.perform(patch("/student/" + student.id() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Students.SECOND.saveStudentRequest().password()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "should not update password when student not exist"() {
        expect: "for cleared db, a student's password should not be updated at PATCH /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(patch("/student/" + UUID.randomUUID() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Students.SECOND.saveStudentRequest().password()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "should not update password when invalid"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "his password should not be updated at PATCH /student/{id}"
        def errorMessage = ErrorMessage.INVALID_CREDENTIALS
        mockMvc.perform(patch("/student/" + student.id() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(' '))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "should not update password when no change"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "his password should not be updated at PATCH /student/{id}"
        def errorMessage = ErrorMessage.PASSWORD_NOT_UPDATED
        mockMvc.perform(patch("/student/" + student.id() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Students.FIRST.saveStudentRequest().password()))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }

    def "delete student"() {
        given: "a student exists in db"
        def student = studentRepository.save(Students.FIRST.studentToAdd())

        expect: "this student can be deleted at DELETE /student/{id}"
        mockMvc.perform(delete("/student/" + student.id()))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andExpect(jsonPath('$').doesNotExist())
    }

    def "should not delete student when not exist"() {
        expect: "for cleared db, a student should not be deleted at DELETE /student/{id}"
        def errorMessage = ErrorMessage.STUDENT_NOT_FOUND
        mockMvc.perform(delete("/student/" + UUID.randomUUID()))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath('$').isNotEmpty())
                .andExpect(jsonPath('\$.code').value(errorMessage.name()))
                .andExpect(jsonPath('\$.message').value(errorMessage.message()))
    }
}
