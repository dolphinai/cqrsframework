package com.github.dolphinai.cqrsframework.common.exception;

/**
 * 无授权访问异常.
 */
public final class AuthorizationException extends AuthenticationException {

  public AuthorizationException() {
    super();
  }

  public AuthorizationException(String message) {
    super(message);
  }

  public AuthorizationException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
