package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

import java.util.Objects;

/**
 *
 */
public class CustomBusinessException extends AppRuntimeException {

  private String code;

  public CustomBusinessException() {
    super();
  }

  public CustomBusinessException(final String message) {
    super(message);
  }

  public CustomBusinessException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public CustomBusinessException(final Throwable throwable) {
    super(throwable);
  }

  public String getCode() {
    return code;
  }

  public CustomBusinessException withCode(final String errorCode) {
    Objects.requireNonNull(errorCode);
    this.code = errorCode;
    return this;
  }

  public final CustomBusinessException of(final String errorCode) {
    return new CustomBusinessException().withCode(errorCode);
  }
}
