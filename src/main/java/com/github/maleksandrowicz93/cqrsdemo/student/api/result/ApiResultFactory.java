package com.github.maleksandrowicz93.cqrsdemo.student.api.result;

import java.util.Map;

public interface ApiResultFactory<T> {

    ApiResult<T> create(ResultCode code);
    ApiResult<T> create(ResultCode code, T value);
    ApiResult<T> create(ResultCode code, Map<ResultProperty, String> properties);
}
