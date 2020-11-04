package com.github.dolphinai.cqrsframework.commons.crypto;

import com.github.dolphinai.cqrsframework.commons.util.StringHelper;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class DigestUtils {

  private DigestUtils() {
  }

  public static String hexEncode(final byte[] originalBytes) {
    if (originalBytes == null || originalBytes.length <= 0) {
      return null;
    }
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < originalBytes.length; i++) {
      String hex = Integer.toHexString(originalBytes[i] & 0xFF);
      if (hex.length() < 2) {
        result.append(0);
      }
      result.append(hex);
    }
    return result.toString().toUpperCase();
  }

  public static byte[] hexDecode(final String hex) {
    if (hex == null || hex.length() == 0) {
      return null;
    }
    //计算长度
    int length = hex.length() / 2;
    //分配存储空间
    byte[] result = new byte[length];
    for (int i = 0; i < length; i++) {
      int position = i * 2 + 1;
      result[i] = (byte) (Integer.decode("0x" + hex.substring(i * 2, position) + hex.substring(position, position + 1)) & 0xFF);
    }
    return result;
  }

  public static String md5(final byte[] original) {
    byte[] data = digest("MD5", original);
    return hexEncode(data);
  }

  public static String md5(final String original) {
    return md5(StringHelper.getBytesUtf8(original));
  }

  public static String sha(final byte[] original) {
    byte[] data = digest("SHA", original);
    return hexEncode(data);
  }

  public static String sha(final String original) {
    return sha(StringHelper.getBytesUtf8(original));
  }

  public static String sha256(final byte[] original) {
    byte[] data = digest("SHA-256", original);
    return hexEncode(data);
  }

  public static String sha256(final String original) {
    return sha256(StringHelper.getBytesUtf8(original));
  }

  public static String sha384(final byte[] original) {
    byte[] data = digest("SHA-256", original);
    return hexEncode(data);
  }

  public static String sha384(final String original) {
    return sha384(StringHelper.getBytesUtf8(original));
  }

  public static String sha512(final byte[] original) {
    final byte[] data = digest("SHA-512", original);
    return hexEncode(data);
  }

  public static String sha512(final String original) {
    return sha512(StringHelper.getBytesUtf8(original));
  }

  private static byte[] digest(final String algorithm, final byte[] original) {
    MessageDigest instance;
    try {
      instance = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    instance.update(original);
    return instance.digest();
  }

  public static byte[] digest(final String algorithm, final InputStream original) throws IOException {
    MessageDigest instance;
    try {
      instance = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    byte[] buffer = new byte[1024];
    for (int read = original.read(buffer, 0, 1024); read > -1; read = original.read(buffer, 0, 1024)) {
      instance.update(buffer, 0, read);
    }
    return instance.digest();
  }
}
