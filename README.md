# cqrs-demo

This application is my first cqrs pattern based project. 
Whole logic is just a simple CRUD with the following actions:
 - add a new student, conditions:
   - user should not exist in db
   - both email and password cannot be blank
 - get all students, conditions:
   - user can pass page size and page number
   - default page number is 0 and page size is 10
 - get a student by id, conditions:
   - student should exist in db
 - edit student's data, conditions:
   - student should exist in db
   - both email and password cannot be blank
 - update student's password
   - student should exist in db
   - password cannot be blank
 - delete a student, conditions:
   - student should exist in db
   - if does not exist, do nothing

You can review application API with api-spec/student-api.yml file.

**Technology stack**
- TDD
- Groovy
- Spock
- Unit tests
- Integration Tests
- Open API
- Liquibase
- H2
- SpringBoot 3
- CQRS
- lombok
- MapStruct

**TODO**
- Vavr
- i18n
- Angular UI
- Cypress e2e tests