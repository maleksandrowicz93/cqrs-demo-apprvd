package com.github.maleksandrowicz93.cqrsdemo.repository;

import java.util.List;
import java.util.Optional;

public interface QueryRepository<T, ID> {

    boolean existsById(ID id);
    List<T> findAll(int page, int size);
    Optional<T> findById(ID id);
}
