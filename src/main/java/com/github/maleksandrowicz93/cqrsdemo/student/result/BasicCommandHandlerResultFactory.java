package com.github.maleksandrowicz93.cqrsdemo.student.result;

import java.util.Map;

public abstract class BasicCommandHandlerResultFactory<T> implements CommandHandlerResultFactory<T> {

    @Override
    public CommandHandlerResult<T> create(T value, ResultCode code) {
        return CommandHandlerResult.<T>builder()
                .value(value)
                .code(code)
                .build();
    }

    @Override
    public CommandHandlerResult<T> create(ResultCode code) {
        return CommandHandlerResult.<T>builder()
                .code(code)
                .build();
    }

    @Override
    public CommandHandlerResult<T> create(ResultCode code, Map<ResultProperty, String> properties) {
        return CommandHandlerResult.<T>builder()
                .code(code)
                .properties(properties)
                .build();
    }
}
