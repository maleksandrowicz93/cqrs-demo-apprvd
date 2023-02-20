package com.github.maleksandrowicz93.cqrsdemo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QueryRepository<T, ID> {

    boolean existsById(ID id);
    Page<T> findAll(Pageable pageable);
    Optional<T> findById(ID id);
}
