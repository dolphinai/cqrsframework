package com.github.dolphinai.cqrsframework.common.exception;

import java.util.Objects;

/**
 *
 */
public class CustomException extends RuntimeException {

  private String code;
  private String path;

  public CustomException() {
    super();
  }

  public CustomException(final String message) {
    super(message);
  }

  public CustomException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CustomException(final Throwable cause) {
    super(cause);
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
    Objects.requireNonNull(errorPath);
    this.path = errorPath;
    return this;
  }

  public final CustomException of(final String errorCode) {
    return new CustomException().withCode(errorCode);
  }
}
