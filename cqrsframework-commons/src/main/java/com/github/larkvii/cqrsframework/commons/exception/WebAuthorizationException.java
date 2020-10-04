package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

/**
 * 无授权访问异常.
 */
public final class WebAuthorizationException extends AppRuntimeException {

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
