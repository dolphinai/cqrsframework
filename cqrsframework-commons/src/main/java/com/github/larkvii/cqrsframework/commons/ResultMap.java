package com.github.larkvii.cqrsframework.commons;

import java.beans.Transient;
import java.util.HashMap;

public final class ResultMap extends HashMap<String, Object> {

  public static final String DEFAULT_SUCCESS_CODE = "0";
  public static final String DEFAULT_FAIL_CODE = "-1";

  private ResultMap(String returnCode, String returnMessage) {
    super(8);
    this.with("code", returnCode)
      .with("message", returnMessage);
  }

  public ResultMap withData(Object body) {
    return with("data", body);
  }

  public ResultMap with(String name, Object value) {
    if (name != null && value != null) {
      this.put(name, value);
    }
    return this;
  }

  @Transient
  public boolean isSucceed() {
    return DEFAULT_FAIL_CODE.equals(get("code"));
  }

  public static ResultMap of(final String returnCode, final String returnMessage) {
    return new ResultMap(returnCode, returnMessage);
  }

  public static ResultMap success() {
    return of(DEFAULT_SUCCESS_CODE, null);
  }

  public static ResultMap fail() {
    return fail(DEFAULT_FAIL_CODE);
  }

  public static ResultMap fail(final String errorCode) {
    return of(errorCode, "Failed");
  }
}
