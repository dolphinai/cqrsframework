package com.github.larkvii.cqrsframework.commons.exception;

/**
 *
 */
public class SessionTimeOutException extends WebAuthenticationException {

  public SessionTimeOutException() {
    super();
  }

  public SessionTimeOutException(String message) {
    super(message);
  }

  public SessionTimeOutException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

}
