package com.github.dolphinai.cqrsframework.commons.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public final class RandomGUID {

  private static final int PAD_BELOW = 0x10;
  private static final int TWO_BYTES = 0xFF;

  private final Random random;
  private final String seed;
  private String raw;

  /*
   * Default constructor.  With no specification of security option,
   * this constructor defaults to lower security, high performance.
   */
  public RandomGUID() {
    this(false);
  }

  /*
   * Constructor with security option.  Setting secure true
   * enables each random number generated to be cryptographically
   * strong.  Secure false defaults to the standard Random function seeded
   * with a single cryptographically strong random number.
   */
  public RandomGUID(boolean secure) {
    if (secure) {
      random = new SecureRandom();
    } else {
      SecureRandom secureRandom = new SecureRandom();
      random = new Random(secureRandom.nextLong());
    }
    try {
      this.seed = InetAddress.getLocalHost().toString();
    } catch (UnknownHostException e) {
      throw new IllegalStateException(e);
    }
  }

  public String getRaw() {
    return raw;
  }

  public String next() {
    this.raw = generateRandomGUID();
    return this.raw;
  }

  /*
   * Method to generate the random GUID
   */
  private String generateRandomGUID() {
    StringBuffer valueBuilder = new StringBuffer(128);
    long time = System.currentTimeMillis();
    long rand = random.nextLong();
    valueBuilder.append(seed);
    valueBuilder.append(":");
    valueBuilder.append(time);
    valueBuilder.append(":");
    valueBuilder.append(rand);

    // md5.
    MessageDigest md5 = null;
    try {
      md5 = MessageDigest.getInstance("MD5");
    } catch (NoSuchAlgorithmException e) {
      throw new IllegalStateException(e);
    }
    byte[] valueBytes = StringHelper.getBytesUtf8(valueBuilder.toString());
    md5.update(valueBytes);
    byte[] array = md5.digest();
    StringBuffer result = new StringBuffer(32);
    for (int j = 0; j < array.length; ++j) {
      int b = array[j] & TWO_BYTES;
      if (b < PAD_BELOW) {
        result.append('0');
      }
      result.append(Integer.toHexString(b));
    }
    return result.toString();
  }

  /*
   * Convert to the standard format for GUID
   * (Useful for SQL Server UniqueIdentifiers, etc.)
   * Example: C2FEEEAC-CFCD-11D1-8B05-00600806D9B6
   */
  public String toString() {
    if (this.raw == null || this.raw.length() == 0) {
      this.raw = generateRandomGUID();
    }
    final String value = this.raw.toUpperCase();
    return value.substring(0, 8) + "-"
      + value.substring(8, 12) + "-"
      + value.substring(12, 16) + "-"
      + value.substring(16, 20) + "-"
      + value.substring(20);
  }
}
