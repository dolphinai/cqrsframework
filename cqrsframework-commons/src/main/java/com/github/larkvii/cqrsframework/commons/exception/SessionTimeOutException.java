package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

/**
 *
 */
public class SessionTimeOutException extends AppRuntimeException {

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
