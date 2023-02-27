package com.github.maleksandrowicz93.cqrsdemo.student


import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.SaveStudentRequest
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentData
import com.github.maleksandrowicz93.cqrsdemo.student.port.incoming.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.Student

interface StudentFactory {

    SaveStudentRequest saveStudentRequest()

    StudentIdentification studentIdentification(UUID id)

    StudentData studentData(UUID id)

    Student studentToAdd()
}