package com.github.dolphinai.cqrsframework.common.util;

import org.springframework.lang.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 *
 */
@SuppressWarnings("unchecked")
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

  public static String orEmpty(@Nullable final Object value) {
    return value == null ? EMPTY : value.toString();
  }

  public static <T> T orDefault(@Nullable final Object value, final T defaultValue) {
    if (value == null || defaultValue == null) {
      return defaultValue;
    }
    return (T) value;
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

  public static String urlEncode(final String urlText) {
    String result;
    try {
      result = URLEncoder.encode(urlText, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  public static String base64Encode(final byte[] plainBytes) {
    return Base64.getEncoder().encodeToString(plainBytes);
  }

  public static byte[] base64Decode(final String base64String) {
    return Base64.getDecoder().decode(base64String);
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
