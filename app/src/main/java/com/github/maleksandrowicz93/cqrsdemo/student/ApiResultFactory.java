package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultCode;
import com.github.maleksandrowicz93.cqrsdemo.student.enums.ResultProperty;

import java.util.Map;

public interface ApiResultFactory<T> {

    ApiResult<T> create(T value);
    ApiResult<T> create(ResultCode code);
    ApiResult<T> create(ResultCode code, Map<ResultProperty, String> properties);
}
