# cqrs-demo

This application is my first cqrs pattern based project. 
Whole logic is just a simple CRUD with the following actions:
 - add a new student, conditions:
   - user cannot exist in db
   - both email and password cannot be null or blank
 - get all students
 - get a student by id, conditions:
   - student should exist in db
 - edit student's data, conditions:
   - email cannot be null or blank
   - student should exist in db
 - update student's password
   - password cannot be null or blank
   - student should exist in db
 - delete a student, conditions:
   - student should exist in db

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

**TODO**
- Vavr
- Angular UI
- Cypress e2e tests