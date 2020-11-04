package com.github.larkvii.cqrsframework.commons.util;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public final class StringHelper {
  private StringHelper() {
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
