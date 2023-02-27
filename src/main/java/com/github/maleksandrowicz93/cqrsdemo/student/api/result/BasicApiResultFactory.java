package com.github.maleksandrowicz93.cqrsdemo.student.api.result;

import java.util.Map;

abstract class BasicApiResultFactory<T> implements ApiResultFactory<T> {

    @Override
    public ApiResult<T> create(ResultCode code) {
        return ApiResult.<T>builder()
                .code(code)
                .build();
    }

    @Override
    public ApiResult<T> create(ResultCode code, T value) {
        return ApiResult.<T>builder()
                .value(value)
                .code(code)
                .build();
    }

    @Override
    public ApiResult<T> create(ResultCode code, Map<ResultProperty, String> properties) {
        return ApiResult.<T>builder()
                .code(code)
                .properties(properties)
                .build();
    }
}
