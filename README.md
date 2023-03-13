# cqrs-demo

## Introduction
This application is my first cqrs pattern based project. Whole logic is just a simple CRUD.
I am aware that used architectures and patterns are overkill from technical point of view, 
but it is just for learning basics of many new concepts. I fully enjoyed development of this project.
Through consecutive iterations of ploughing code, I get to rediscover the world many times.

## Special greetings
During my journey I had a great guide. Thanks a lot Jonasz Czerepko (https://github.com/Jonarzz) for leading 
my growing here. I appreciate every Code Review you made and consider them very valuable.

## Business functionalities

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

## Project goals
* practicing programming good practices
* learning designing convenient API
* practicing TDD and BDD
* learning spock and groovy
* learning CQRS
* learning Hexagonal Architecture
* learning DDD
* Learning managing dependencies with bom

## Rest API
You can review application API with api-spec/student-api.yml file.
You can paste its content to swagger editor https://editor.swagger.io 
and explore API in user friendly, interactive way.

## Running server
Prerequisites:
- running docker daemon
- free 8000 port

Go to project root directory, and build docker image by running `docker build -t cqrs-demo-be .`. 
Then run `docker images`, copy IMAGE ID, and run container with command `docker run -p 8000:8000 <IMAGE_ID>`.

## Project stack

**Technology stack**
- Groovy
- Spock
- Lombok
- MapStruct
- Open API
- Liquibase
- H2
- SpringBoot 3

**Methodologies**
- TDD
- BDD
- CQRS
- Hexagonal Architecture
- DDD