package com.github.maleksandrowicz93.cqrsdemo.repository;

import java.util.Optional;

public interface QueryRepository<T, ID> {

    boolean existsById(ID id);
    ResultPage<T> findAll(int page, int size);
    Optional<T> findById(ID id);
}
