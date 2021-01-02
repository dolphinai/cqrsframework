package com.github.dolphinai.cqrsframework.web.api;

import lombok.Value;

/**
 */
@Value
class ApiResultImpl<T> implements ApiResult<T> {

  private T data;

}
