package com.github.maleksandrowicz93.cqrsdemo;

public interface Converter<T, R> {
    R convert(T t);
}
