package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.rest.dto.SaveStudentRequest

interface StudentFactory {

    SaveStudentRequest saveStudentRequest()
    StudentIdentification studentIdentification(UUID id)
    StudentData studentData(UUID id)
    Student studentToAdd()
}