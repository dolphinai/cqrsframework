package com.github.dolphinai.cqrsframework.commons.exception;

/**
 * 无授权访问异常.
 */
public final class AppAuthorizationException extends AppAuthenticationException {

  public AppAuthorizationException() {
    super();
  }

  public AppAuthorizationException(String message) {
    super(message);
  }

  public AppAuthorizationException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
