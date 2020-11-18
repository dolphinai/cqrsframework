package com.github.dolphinai.cqrsframework.common.exception;

/**
 * 登录身份验证异常.
 */
public class AuthenticationException extends RuntimeException {

  public AuthenticationException() {
    super();
  }

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public AuthenticationException(final Throwable cause) {
    super(cause);
  }

}
