package com.github.maleksandrowicz93.cqrsdemo.repository;

import lombok.Value;

import java.util.List;
import java.util.function.Function;

@Value
public class ResultPage<T> {

    int totalPages;
    List<T> content;

    public  <R> ResultPage<R> map(Function<? super T, ? extends R> converter) {
        var convertedContent = content.stream()
                .map(converter)
                .map(r -> (R) r)
                .toList();
        return new ResultPage<>(totalPages, convertedContent);
    }
}
