package com.github.dolphinai.cqrsframework.web.api;

/**
 *
 */
public final class ApiException extends RuntimeException {

  private String module;
  private String errorCode;

  public ApiException() {
    super();
  }

  public ApiException(final String message) {
    super(message);
  }

  public ApiException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public ApiException(final Throwable cause) {
    super(cause);
  }

  public ApiException withErrorCode(final String code) {
    this.errorCode = code;
    return this;
  }

  public ApiException withModule(final String module) {
    this.module = module;
    return this;
  }

  public String getErrorCode() {
    return errorCode;
  }

  public String getModule() {
    return module;
  }

  public static ApiException of(final String errorCode) {
    return new ApiException().withErrorCode(errorCode);
  }

  public static ApiException of(final String errorCode, final Throwable cause) {
    return new ApiException(cause).withErrorCode(errorCode);
  }

}
