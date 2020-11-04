package com.github.dolphinai.cqrsframework.commons.util;

import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 *
 */
public final class StringHelper {

  public static final String EMPTY = "";

  private StringHelper() {
  }

  /**
   * Verify if the text is blank (whitespace only)
   *
   * @param value Text
   * @return Indicator
   */
  public static boolean isBlank(@Nullable final String value) {
    return value == null || value.trim().length() == 0;
  }

  /**
   *
   * Verify if the text is empty String.
   *
   * @param value Text
   * @return Indicator
   */
  public static boolean isEmpty(@Nullable final String value) {
    return value == null || value.length() == 0;
  }

  /**
   * Verify that if the text just contains numeric.
   *
   * @param value Text
   * @return Indicator
   */
  public static boolean isNumeric(final String value) {
    if (isBlank(value)) {
      return false;
    }
    int size = value.length();
    for (int i = 0; i < size; i++) {
      char ch = value.charAt(i);
      if (ch < '0' || ch > '9') {
        return false;
      }
    }
    return true;
  }

  public static String orEmpty(@Nullable final String value) {
    return value == null ? EMPTY : value;
  }

  public static String getStringUtf8(final byte[] bytes) {
    Objects.requireNonNull(bytes);
    return new String(bytes, StandardCharsets.UTF_8);
  }

  public static byte[] getBytesUtf8(final String text) {
    Objects.requireNonNull(text);
    return text.getBytes(StandardCharsets.UTF_8);
  }
}
