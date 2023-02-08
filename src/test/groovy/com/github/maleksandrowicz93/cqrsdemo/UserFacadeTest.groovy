package com.github.maleksandrowicz93.cqrsdemo

import spock.lang.Specification

class UserFacadeTest extends Specification {

    def "get all students"() {
        given: "2 students exist in db"
        when: "user tries to retrieve them"
        then: "gets exactly these students and no more"
    }

    def "add new student"() {
        when: "user tries to add a new student"
        then: "this student should be successfully added"
    }

    def "get student"() {
        given: "a student exists in db"
        when: "user tries to retrieve student's data by student's id"
        then: "gets his correct data"
    }

    def "update student"() {
        given: "a student exists in db"
        when: "user tries to edit student's data"
        then: "data is successfully edited"
    }

    def "update password"() {
        given: "a student exists in db"
        when: "user tries to update student's password"
        then: "password is successfully updated"
    }

    def "delete student"() {
        given: "a student exists in db"
        when: "user tries to delete student's account"
        then: "account is successfully deleted"
    }
}
