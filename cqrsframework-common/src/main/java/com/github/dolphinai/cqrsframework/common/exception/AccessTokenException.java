package com.github.dolphinai.cqrsframework.common.exception;

/**
 */
public final class AccessTokenException extends RuntimeException {

  public AccessTokenException() {
    super();
  }

  public AccessTokenException(String message) {
    super(message);
  }

  public AccessTokenException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AccessTokenException(final Throwable cause) {
    super(cause);
  }

}
