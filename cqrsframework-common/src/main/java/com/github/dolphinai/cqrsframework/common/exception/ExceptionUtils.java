package com.github.dolphinai.cqrsframework.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

/**
 *
 */
public final class ExceptionUtils {

  private ExceptionUtils() {
  }

  public static String printStackTrace(final Throwable error) {
    Objects.requireNonNull(error);
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    error.printStackTrace(pw);
    return sw.toString();
  }

}
