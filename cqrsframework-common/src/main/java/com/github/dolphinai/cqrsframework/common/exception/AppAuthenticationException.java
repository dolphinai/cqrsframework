package com.github.dolphinai.cqrsframework.common.exception;

/**
 * 登录身份验证异常.
 */
public class AppAuthenticationException extends RuntimeException {

  public AppAuthenticationException() {
    super();
  }

  public AppAuthenticationException(String message) {
    super(message);
  }

  public AppAuthenticationException(final String message, final Throwable throwable) {
    super(message, throwable);
  }
}
