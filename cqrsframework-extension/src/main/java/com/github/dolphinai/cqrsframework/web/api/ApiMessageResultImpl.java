package com.github.dolphinai.cqrsframework.web.api;

import lombok.Value;

/**
 */
@Value
class ApiMessageResultImpl implements ApiMessageResult {

  private String code;
  private String message;

}
