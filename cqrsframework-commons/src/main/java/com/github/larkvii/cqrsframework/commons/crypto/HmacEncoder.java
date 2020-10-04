package com.github.larkvii.cqrsframework.commons.crypto;

import com.github.larkvii.cqrsframework.commons.util.Charsets;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Objects;

/**
 * Hash 编码.
 */
public final class HmacEncoder {

  private final String algorithm;
  private final byte[] keyBytes;

  public HmacEncoder(final String key) {
    this("HmacSHA1", key);
  }

  public HmacEncoder(final String algorithm, final String key) {
    Objects.requireNonNull(algorithm);
    Objects.requireNonNull(key);
    this.algorithm = algorithm;
    this.keyBytes = key.getBytes(Charsets.UTF_8);
  }

  public String encode(final String text) {
    return encode(text, null);
  }

  public String encode(final String text, final String salt) {
    byte[] hashedBytes = encode(Charsets.getBytesUtf8(text), salt);
    if (hashedBytes != null) {
      return Base64.getEncoder().encodeToString(hashedBytes);
    }
    return null;
  }

  public byte[] encode(final byte[] plainBytes, final String salt) {
    Objects.requireNonNull(plainBytes);
    byte[] saltBytes = null;
    if (salt != null) {
      saltBytes = salt.getBytes(Charsets.UTF_8);
    }
    // hash
    SecretKey secretKey = new SecretKeySpec(keyBytes, algorithm);
    byte[] hashedBytes = null;
    try {
      Mac mac = Mac.getInstance(algorithm);
      mac.init(secretKey);
      if (saltBytes != null) {
        mac.update(saltBytes);
      }
      hashedBytes = mac.doFinal(plainBytes);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
    return hashedBytes;
  }
}
