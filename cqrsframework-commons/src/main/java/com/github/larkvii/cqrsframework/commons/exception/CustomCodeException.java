package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

import java.util.Objects;

/**
 *
 */
public class CustomCodeException extends AppRuntimeException {

  private String code;

  public CustomCodeException() {
    super();
  }

  public CustomCodeException(final String message) {
    super(message);
  }

  public CustomCodeException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public CustomCodeException(final Throwable throwable) {
    super(throwable);
  }

  public String getCode() {
    return code;
  }

  public CustomCodeException withCode(final String code) {
    Objects.requireNonNull(code);
    this.code = code;
    return this;
  }
}
