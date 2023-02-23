package com.github.maleksandrowicz93.cqrsdemo.interfaces;

public interface WriteRepository<T, ID> {

    T save(T entity);
    void deleteById(ID id);
}
