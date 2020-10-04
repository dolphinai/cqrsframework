package com.github.larkvii.cqrsframework.commons.util;

import java.nio.charset.Charset;
import java.util.Objects;

/**
 *
 */
public final class Charsets {
  private Charsets() {
  }

  public static final Charset US_ASCII = Charset.forName("US-ASCII");
  public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
  public static final Charset UTF_8 = Charset.forName("UTF-8");
  public static final Charset UTF_16 = Charset.forName("UTF-16");

  public static String getStringUtf8(final byte[] bytes) {
    Objects.requireNonNull(bytes);
    return new String(bytes, UTF_8);
  }

  public static byte[] getBytesUtf8(final String text) {
    Objects.requireNonNull(text);
    return text.getBytes(UTF_8);
  }
}
