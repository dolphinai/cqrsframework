package com.github.dolphinai.cqrsframework.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public interface ApiResult<T> extends Serializable {

  T getData();

  static <R> ApiResult<R> of(final R result) {
    return new ApiResultImpl<>(result);
  }

  static <T> ApiResult<List<T>> of(final List<T> list) {
    if (list == null || list.isEmpty()) {
      return new ApiResultImpl<>(Collections.emptyList());
    } else {
      return new ApiResultImpl<>(list);
    }
  }

  static <T> ApiResult<T[]> arrayOf(final T[] values) {
    return new ApiResultImpl<>(values);
  }
}
