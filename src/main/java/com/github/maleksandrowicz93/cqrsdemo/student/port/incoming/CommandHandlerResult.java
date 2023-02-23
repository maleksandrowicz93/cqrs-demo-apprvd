package com.github.maleksandrowicz93.cqrsdemo.student.port.incoming;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.Map;
import java.util.Optional;

@Value
@Builder(access = AccessLevel.PACKAGE)
public class CommandHandlerResult<T> {

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
