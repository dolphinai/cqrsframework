package com.github.dolphinai.cqrsframework.commons.exception;

import com.github.dolphinai.cqrsframework.commons.AppRuntimeException;

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
