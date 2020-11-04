package com.github.larkvii.cqrsframework.commons.util;

import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public final class StringHelper {
  private StringHelper() {
  }

  /**
   * 判断是否纯数字的字符串.
   *
   * @param value
   * @return
   */
  public static boolean isNumeric(final String value) {
    if (!StringUtils.hasText(value)) {
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

  public static String orEmpty(final String value) {
    return Optional.ofNullable(value).orElse("");
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
