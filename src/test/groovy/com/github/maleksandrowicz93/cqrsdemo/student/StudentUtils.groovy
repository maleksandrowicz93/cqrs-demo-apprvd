package com.github.maleksandrowicz93.cqrsdemo.student


import org.springframework.data.domain.PageRequest

class StudentUtils {

    static final PageRequest PAGE_REQUEST = PageRequest.of(0, 10)

    private StudentUtils() {}

    static def cleanRepository(StudentRepository studentRepository) {
        def pageNumber = studentRepository.findAll(PAGE_REQUEST).getTotalPages()
        for (i in 0..<pageNumber) {
            studentRepository.findAll(PAGE_REQUEST).getContent()
                    .forEach(student -> studentRepository.deleteById(student.id()))
        }
    }
}
