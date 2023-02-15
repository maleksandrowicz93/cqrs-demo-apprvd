package com.github.maleksandrowicz93.cqrsdemo.interfaces;

import java.util.Optional;

public interface WriteRepository<T, ID> {

    boolean existsById(ID id);
    Optional<T> findById(ID id);
    <S extends T> S save(S entity);
    void deleteById(ID id);
}
