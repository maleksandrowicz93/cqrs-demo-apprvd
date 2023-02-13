package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.SaveStudentRequest
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentDto
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification

interface StudentUtils {

    SaveStudentRequest saveStudentRequest()
    StudentIdentification studentIdentification(UUID id)
    StudentDto studentDto(UUID id)
    Student studentToAdd()
}