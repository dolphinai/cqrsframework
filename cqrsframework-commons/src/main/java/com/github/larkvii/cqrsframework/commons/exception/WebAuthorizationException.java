package com.github.larkvii.cqrsframework.commons.exception;

/**
 * 无授权访问异常.
 */
public final class WebAuthorizationException extends WebAuthenticationException {

  public WebAuthorizationException() {
    super();
  }

  public WebAuthorizationException(String message) {
    super(message);
  }

  public WebAuthorizationException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
