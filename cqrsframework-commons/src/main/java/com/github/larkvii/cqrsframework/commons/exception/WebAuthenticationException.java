package com.github.larkvii.cqrsframework.commons.exception;

/**
 * 登录身份验证异常.
 */
public class WebAuthenticationException extends RuntimeException {

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
