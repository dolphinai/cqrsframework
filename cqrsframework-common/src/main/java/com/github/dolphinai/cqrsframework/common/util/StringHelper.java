package com.github.dolphinai.cqrsframework.common.util;

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

  public static boolean isNotBlank(@Nullable final String value) {
    return !isBlank(value);
  }

  /**
   *
   * Verify if the text is empty String.
   *
   * @param value Text
   * @return Indicator
   */
  public static boolean isEmpty(@Nullable final Object value) {
    return value == null || value.toString().length() == 0;
  }

  public static boolean isNotEmpty(@Nullable final Object value) {
    return !isEmpty(value);
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

  public static String ifEmpty(@Nullable final String value) {
    return value == null ? EMPTY : value;
  }

  public static String padStart(final String source, int minLength, final char padChar) {
    Objects.requireNonNull(source);
    if (source.length() >= minLength) {
      return source;
    }
    StringBuilder builder = new StringBuilder(minLength);
    for (int i = source.length(); i < minLength; ++i) {
      builder.append(padChar);
    }
    builder.append(source);
    return builder.toString();
  }

  public static String padEnd(final String source, int minLength, final char padChar) {
    Objects.requireNonNull(source);
    if (source.length() >= minLength) {
      return source;
    }
    StringBuilder builder = new StringBuilder(minLength);
    builder.append(source);
    for (int i = source.length(); i < minLength; ++i) {
      builder.append(padChar);
    }
    return builder.toString();
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
