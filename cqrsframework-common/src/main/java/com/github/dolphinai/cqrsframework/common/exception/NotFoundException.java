package com.github.dolphinai.cqrsframework.common.exception;

import com.github.dolphinai.cqrsframework.common.AppRuntimeException;

public final class NotFoundException extends AppRuntimeException {

  public NotFoundException() {
    super();
  }

  public NotFoundException(String message) {
    super(message);
  }

  public NotFoundException(String message, Throwable innerException) {
    super(message, innerException);
  }

}
