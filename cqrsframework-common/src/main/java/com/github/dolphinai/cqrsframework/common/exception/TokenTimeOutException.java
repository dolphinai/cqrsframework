package com.github.dolphinai.cqrsframework.common.exception;

/**
 *
 */
public final class TokenTimeOutException extends AuthenticationException {

  public TokenTimeOutException() {
    super();
  }

  public TokenTimeOutException(String message) {
    super(message);
  }

  public TokenTimeOutException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public TokenTimeOutException(final Throwable cause) {
    super(cause);
  }

}
