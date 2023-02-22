package com.github.maleksandrowicz93.cqrsdemo.student.result;

import java.util.Map;

public interface CommandHandlerResultFactory<T> {
    CommandHandlerResult<T> create(T value, ResultCode code);
    CommandHandlerResult<T> create(ResultCode code);
    CommandHandlerResult<T> create(ResultCode code, Map<ResultProperty, String> properties);
}
