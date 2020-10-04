package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

/**
 * 登录身份验证异常.
 */
public final class WebAuthenticationException extends AppRuntimeException {

  public WebAuthenticationException() {
    super();
  }

  public WebAuthenticationException(String message) {
    super(message);
  }

  public WebAuthenticationException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
