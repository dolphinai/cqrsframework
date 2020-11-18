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

  public AppException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AppException(final Throwable cause) {
    super(cause);
  }

}
