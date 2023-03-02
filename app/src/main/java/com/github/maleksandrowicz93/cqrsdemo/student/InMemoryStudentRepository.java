package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.repository.ResultPage;
import lombok.experimental.FieldDefaults;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@FieldDefaults(makeFinal = true)
class InMemoryStudentRepository implements StudentWriteRepository, StudentQueryRepository {
    Map<UUID, Student> students = new HashMap<>();

    @Override
    public Optional<UUID> findStudentIdByEmail(String email) {
        return students.entrySet().stream()
                .filter(e -> e.getValue().email().equals(email))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    @Override
    public boolean existsById(UUID uuid) {
        return students.containsKey(uuid);
    }

    @Override
    public ResultPage<Student> findAll(int page, int size) {
        var totalPages = students.size() / size;
        var skipValue = page * size;
        var limitValue = skipValue + size;
        List<Student> studentList = students.values().stream()
                .sorted(Comparator.comparing(Student::email))
                .skip(skipValue)
                .limit(limitValue)
                .toList();
        return new ResultPage<>(totalPages, studentList);
    }

    @Override
    public Optional<Student> findById(UUID uuid) {
        return Optional.ofNullable(students.get(uuid));
    }

    @Override
    public Student save(Student entity) {
        if (entity.id() == null) {
            entity.id(UUID.randomUUID());
        }
        students.put(entity.id(), entity);
        return students.get(entity.id());
    }

    @Override
    public void deleteById(UUID uuid) {
        students.remove(uuid);
    }
}
