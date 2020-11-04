package com.github.larkvii.cqrsframework.commons.exception;

import com.github.larkvii.cqrsframework.commons.AppRuntimeException;

import java.util.Objects;

/**
 *
 */
public class CustomException extends AppRuntimeException {

  private String code;
  private String path;

  public CustomException() {
    super();
  }

  public CustomException(final String message) {
    super(message);
  }

  public CustomException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

  public CustomException(final Throwable throwable) {
    super(throwable);
  }

  public String getCode() {
    return code;
  }

  public String getPath() {
    return path;
  }

  public CustomException withCode(final String errorCode) {
    Objects.requireNonNull(errorCode);
    this.code = errorCode;
    return this;
  }

  public CustomException withPath(final String errorPath) {
    Objects.requireNonNull(path);
    this.path = path;
    return this;
  }

  public final CustomException of(final String errorCode) {
    return new CustomException().withCode(errorCode);
  }
}
