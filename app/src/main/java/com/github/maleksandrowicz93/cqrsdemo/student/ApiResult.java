package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode;
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

import static lombok.AccessLevel.NONE;
import static lombok.AccessLevel.PACKAGE;

@Value
@Builder(access = PACKAGE)
@AllArgsConstructor(access = PACKAGE)
public class ApiResult<T> {

    @Getter(NONE)
    T value;
    ResultCode code;
    @Getter(NONE)
    Map<ResultProperty, String> properties;

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public String property(ResultProperty resultProperty) {
        return properties.get(resultProperty);
    }
}
