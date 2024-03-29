package com.github.maleksandrowicz93.cqrsdemo.student

import com.github.maleksandrowicz93.cqrsdemo.student.dto.AddStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.EditStudentCommand
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentData
import com.github.maleksandrowicz93.cqrsdemo.student.dto.StudentIdentification
import com.github.maleksandrowicz93.cqrsdemo.student.dto.UpdatePasswordCommand

interface StudentFactory {

    StudentSnapshot studentToAdd()
    StudentSnapshot addedStudent(UUID id)
    AddStudentCommand addStudentCommand()
    EditStudentCommand editStudentCommand(UUID id)
    UpdatePasswordCommand updatePasswordCommand(UUID id)
    DeleteStudentCommand deleteStudentCommand(UUID id)
    StudentIdentification studentIdentification(UUID id)
    StudentData studentData(UUID id)
}