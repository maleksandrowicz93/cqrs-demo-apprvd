package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode;
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ApiResult<T> {

    @Getter(AccessLevel.NONE)
    T value;
    ResultCode code;
    @Getter(AccessLevel.NONE)
    Map<ResultProperty, String> properties;

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public String property(ResultProperty resultProperty) {
        return properties.get(resultProperty);
    }
}
