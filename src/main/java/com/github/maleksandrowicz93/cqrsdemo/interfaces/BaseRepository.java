package com.github.maleksandrowicz93.cqrsdemo.interfaces;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T, ID> {

    List<T> findAll();
    <S extends T> S save(S entity);
    boolean existsById(ID id);
    Optional<T> findById(ID id);
    void deleteById(ID id);
    void deleteAll();
}
