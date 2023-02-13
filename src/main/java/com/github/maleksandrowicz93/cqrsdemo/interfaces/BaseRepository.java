package com.github.maleksandrowicz93.cqrsdemo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BaseRepository<T, ID> {

    Page<T> findAll(Pageable pageable);
    <S extends T> S save(S entity);
    boolean existsById(ID id);
    Optional<T> findById(ID id);
    void deleteById(ID id);
}
