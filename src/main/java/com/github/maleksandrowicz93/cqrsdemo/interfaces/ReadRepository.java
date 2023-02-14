package com.github.maleksandrowicz93.cqrsdemo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReadRepository<T, ID> {

    Page<T> findAll(Pageable pageable);
    boolean existsById(ID id);
    Optional<T> findById(ID id);
}
