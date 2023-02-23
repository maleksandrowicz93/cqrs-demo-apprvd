package com.github.maleksandrowicz93.cqrsdemo.student


import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student

interface StudentFactory {

    SaveStudentRequest saveStudentRequest()

    StudentIdentification studentIdentification(UUID id)

    StudentDto studentDto(UUID id)

    Student studentToAdd()
}