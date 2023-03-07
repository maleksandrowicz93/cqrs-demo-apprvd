package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.dto.DeleteStudentCommand;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DeleteStudentCommandHandler {

    StudentQueryRepository studentQueryRepository;
    StudentWriteRepository studentWriteRepository;

    void handle(DeleteStudentCommand command) {
        log.info("Handling; {}", command);
        var id = command.id();
        if (studentQueryRepository.existsById(id)) {
            studentWriteRepository.deleteById(id);
            log.info("Student with id {} successfully deleted", id);
        } else {
            log.error("Cannot delete student with id {}, because not found", id);
        }
    }
}
