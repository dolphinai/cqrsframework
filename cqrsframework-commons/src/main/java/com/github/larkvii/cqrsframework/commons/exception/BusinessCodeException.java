package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

import java.util.Objects;

/**
 *
 */
public class BusinessCodeException extends AppRuntimeException {

  private String code;
  private String path;

  public BusinessCodeException() {
    super();
  }

  public BusinessCodeException(final String message) {
    super(message);
  }

  public BusinessCodeException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public BusinessCodeException(final Throwable throwable) {
    super(throwable);
  }

  public String getCode() {
    return code;
  }

  public String getPath() {
    return path;
  }

  public BusinessCodeException withCode(final String errorCode) {
    Objects.requireNonNull(errorCode);
    this.code = errorCode;
    return this;
  }

  public BusinessCodeException withPath(final String errorPath) {
    Objects.requireNonNull(path);
    this.path = path;
    return this;
  }

  public final BusinessCodeException of(final String errorCode) {
    return new BusinessCodeException().withCode(errorCode);
  }
}
