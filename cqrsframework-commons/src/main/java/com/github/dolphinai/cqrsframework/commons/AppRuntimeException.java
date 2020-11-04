package com.github.dolphinai.cqrsframework.commons;

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

  public AppRuntimeException(final String message, Throwable throwable) {
    super(message, throwable);
  }

  public AppRuntimeException(final Throwable throwable) {
    super(throwable);
  }

}
