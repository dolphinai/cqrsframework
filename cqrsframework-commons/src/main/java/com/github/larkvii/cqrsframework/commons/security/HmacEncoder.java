package com.github.larkvii.cqrsframework.commons.security;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Objects;

/**
 * Hash 编码.
 */
public final class HmacEncoder {

  private static final String ALGORITHM = "HmacSHA1";
  private final Charset defaultCharset = Charset.forName("utf-8");
  private final byte[] keyBytes;

  public HmacEncoder(final String key) {
    Objects.requireNonNull(key);
    this.keyBytes = key.getBytes(defaultCharset);
  }

  public String encode(final String text) {
    return encode(text, null);
  }

  public String encode(final String text, final String salt) {
    byte[] hashedBytes = encodeToBytes(text, salt);
    if (hashedBytes != null) {
      return Base64.getEncoder().encodeToString(hashedBytes);
    }
    return null;
  }

  public byte[] encodeToBytes(final String text, final String salt) {
    if (text == null) {
      return null;
    }
    byte[] textBytes = text.getBytes(defaultCharset);
    byte[] saltBytes = null;
    if (salt != null) {
      saltBytes = salt.getBytes(defaultCharset);
    }
    // hash
    SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
    byte[] hashedBytes = null;
    try {
      Mac mac = Mac.getInstance(ALGORITHM);
      mac.init(secretKey);
      if (saltBytes != null) {
        mac.update(saltBytes);
      }
      hashedBytes = mac.doFinal(textBytes);
    } catch (Exception e) {
      //log.error("Failed to hash text", e);
    }
    return hashedBytes;
  }


}
