package com.github.dolphinai.cqrsframework.common;

/**
 *
 */
public class AppRuntimeException extends RuntimeException {

  public AppRuntimeException() {
    super();
  }

  public AppRuntimeException(final String message) {
    super(message);
  }

  public AppRuntimeException(final String message, Throwable cause) {
    super(message, cause);
  }

  public AppRuntimeException(final Throwable cause) {
    super(cause);
  }

}
