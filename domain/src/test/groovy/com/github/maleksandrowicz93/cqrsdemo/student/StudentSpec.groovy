package com.github.maleksandrowicz93.cqrsdemo.student

import spock.lang.Specification
import spock.lang.Unroll

class StudentSpec extends Specification {

    def "validate correct snapshot"() {
        given: "snapshot with correct data"
        StudentSnapshot snapshot = SnapshotFactory.studentToAdd()

        expect: "should be correctly validated"
        Student.validateSnapshot(snapshot)
    }

    @Unroll("email: #snapshot.email(), password: #snapshot.password()")
    def "should not validate incorrect snapshot"() {
        when: "snapshot is validated"
        Student.validateSnapshot(snapshot)

        then: "InvalidCredentialsException should be thrown"
        thrown(InvalidCredentialsException)

        where: "snapshot's data is invalid"
        snapshot << SnapshotFactory.incorrectSnapshots()
    }

    def "create Student from snapshot"() {
        given: "snapshot with correct data"
        def snapshot = SnapshotFactory.studentToAdd()

        when: "Student is created from the snapshot"
        def student = Student.fromSnapshot(snapshot)

        then: "should be created correctly"
        student.createSnapshot() == snapshot
    }

    @Unroll("email: #snapshot.email(), password: #snapshot.password()")
    def "should not create Student from incorrect snapshot"() {
        when:
        Student.fromSnapshot(snapshot)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)

        where: "snapshot's data is incorrect"
        snapshot << SnapshotFactory.incorrectSnapshots()
    }

    def "should edit student"() {
        given: "existing student"
        def snapshot = SnapshotFactory.addedStudent()
        def student = Student.fromSnapshot(snapshot)

        and: "new data to edit student"
        def newData = SnapshotFactory.newStudentData()

        when: "student is edited with this data"
        student.editData(newData)

        then: "should be edited correctly"
        student.createSnapshot() == newData
    }

    @Unroll("email: #newData.email(), password: #newData.password()")
    def "should not edit student with incorrect data"() {
        given: "existing student"
        def snapshot = SnapshotFactory.newStudentData()
        def student = Student.fromSnapshot(snapshot)

        when: "student is edited with new data"
        student.editData(newData)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)

        where: "new student data is incorrect"
        newData << SnapshotFactory.incorrectSnapshots()
    }

    def "should update student's password"() {
        given: "existing student"
        def snapshot = SnapshotFactory.addedStudent()
        def student = Student.fromSnapshot(snapshot)

        expect: "student's password can be updated"
        student.updatePassword("newPassword")
    }

    @Unroll("password: #password")
    def "should not update student's password with incorrect value"() {
        given: "existing student"
        def snapshot = SnapshotFactory.addedStudent()
        def student = Student.fromSnapshot(snapshot)

        when: "student's password is updated with new value"
        student.updatePassword(password)

        then: "InvalidCredentialsException is thrown"
        thrown(InvalidCredentialsException)

        where: "new password is incorrect"
        password << [null, " "]
    }
}
