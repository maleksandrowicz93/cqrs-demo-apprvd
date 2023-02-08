package com.github.maleksandrowicz93.cqrsdemo.interfaces;

public interface Converter<T, R> {
    R convert(T t);
}
