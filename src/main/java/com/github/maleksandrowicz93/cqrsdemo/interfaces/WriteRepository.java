package com.github.maleksandrowicz93.cqrsdemo.interfaces;

public interface WriteRepository<T, ID> {

    <S extends T> S save(S entity);
    void deleteById(ID id);
}
