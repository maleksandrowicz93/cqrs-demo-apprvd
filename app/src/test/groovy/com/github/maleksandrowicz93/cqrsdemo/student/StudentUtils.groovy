package com.github.maleksandrowicz93.cqrsdemo.student

class StudentUtils {

    static final int PAGE = 0
    static final int SIZE = 10

    private StudentUtils() {}

    static def cleanRepository(StudentQueryRepository queryRepository, StudentWriteRepository writeRepository) {
        def totalPages = queryRepository.findAll(PAGE, SIZE).totalPages()
        for (i in 0..<totalPages) {
            queryRepository.findAll(i, SIZE).content()
                    .forEach(student -> writeRepository.deleteById(student.id()))
        }
    }
}
