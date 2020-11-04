package com.github.dolphinai.cqrsframework.commons.exception;

import com.github.dolphinai.cqrsframework.commons.AppRuntimeException;

public final class NoRecordException extends AppRuntimeException {

  public NoRecordException() {
    super();
  }

  public NoRecordException(String message) {
    super(message);
  }

  public NoRecordException(String message, Throwable innerException) {
    super(message, innerException);
  }
}
