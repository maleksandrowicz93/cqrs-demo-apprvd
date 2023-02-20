package com.github.maleksandrowicz93.cqrsdemo.student;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Value
@Builder
public class CommandHandlerResult<T> {

    @Getter(AccessLevel.NONE)
    T value;
    ResultCode code;
    @Getter(AccessLevel.NONE)
    Map<ResultProperty, String> properties = new HashMap<>();

    public Optional<T> value() {
        return Optional.ofNullable(value);
    }

    public CommandHandlerResult<T> property(ResultProperty resultProperty, String value) {
        properties.put(resultProperty, value);
        return this;
    }

    public String property(ResultProperty resultProperty) {
        return properties.get(resultProperty);
    }
}
