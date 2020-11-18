package com.github.dolphinai.cqrsframework.common.exception;

import com.github.dolphinai.cqrsframework.common.AppRuntimeException;

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
