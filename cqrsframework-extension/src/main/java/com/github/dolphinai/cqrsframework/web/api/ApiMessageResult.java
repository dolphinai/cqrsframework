package com.github.dolphinai.cqrsframework.web.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface ApiMessageResult extends Serializable {

  String getCode();

  String getMessage();

  static ApiMessageResult of(final String code) {
    return of(code, null);
  }

  static ApiMessageResult of(final String code, final String message) {
    return new ApiMessageResultImpl(code, message);
  }
}
