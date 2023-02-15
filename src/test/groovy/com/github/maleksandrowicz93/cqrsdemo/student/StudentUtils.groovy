package com.github.maleksandrowicz93.cqrsdemo.student


import org.springframework.data.domain.PageRequest

class StudentUtils {

    static final PageRequest PAGE_REQUEST = PageRequest.of(0, 10)

    private StudentUtils() {}

    static def cleanRepository(StudentQueryRepository queryRepository, StudentWriteRepository writeRepository) {
        def pageNumber = queryRepository.findAll(PAGE_REQUEST).getTotalPages()
        for (i in 0..<pageNumber) {
            queryRepository.findAll(PAGE_REQUEST).getContent()
                    .forEach(student -> writeRepository.deleteById(student.id()))
        }
    }
}
