package com.github.dolphinai.cqrsframework.common;

/**
 *
 */
public class AppException extends Exception {

  public AppException() {
    super();
  }

  public AppException(final String message) {
    super(message);
  }

  public AppException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public AppException(final Throwable throwable) {
    super(throwable);
  }

}
