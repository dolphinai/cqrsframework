package com.github.dolphinai.cqrsframework.common.util;

import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * IP Utility.
 */
public final class IpUtils {

  private IpUtils() {
  }

  public static boolean isIpV6(final String ip) {
    return ip != null && ip.indexOf(':') > -1;
  }

  /**
   * IP long value to String.
   *
   * @param ipV4Long IP long value
   * @return  IP address
   */
  public static String toIpV4(final long ipV4Long) {
    int octet3 = (int) ((ipV4Long >> 24) % 256);
    int octet2 = (int) ((ipV4Long >> 16) % 256);
    int octet1 = (int) ((ipV4Long >> 8) % 256);
    int octet0 = (int) ((ipV4Long) % 256);
    return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
  }

  /**
   * IPV4 to Long value.
   *
   * @param ipV4 IPv4 address
   * @return Numeric value
   */
  public static Long toIpV4Long(final String ipV4) {
    // IPv4: 32 bits
    final String[] octets = StringUtils.tokenizeToStringArray(ipV4, "\\.");
    if (octets.length == 4) {
      return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
        + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }
    return 0L;
  }

  public static BigInteger toIpV6Int(final String ipv6){
    int compressIndex = ipv6.indexOf("::");
    if (compressIndex != -1){
      String part1s = ipv6.substring(0, compressIndex);
      String part2s = ipv6.substring(compressIndex + 1);
      BigInteger part1 = toIpV6Int(part1s);
      BigInteger part2 = toIpV6Int(part2s);
      int part1hasDot = 0;
      char[] values = part1s.toCharArray();
      for(char c : values){
        if(c == ':'){
          part1hasDot++;
        }
      }
      return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
    }
    String[] str = ipv6.split(":");
    BigInteger result = BigInteger.ZERO;
    for(int i = 0; i < str.length; i++){
      //::1
      if(str[i].isEmpty()){
        str[i] = "0";
      }
      result = result.add(BigInteger.valueOf(Long.valueOf(str[i], 16)).shiftLeft(16 * (str.length - i - 1)));
    }
    return result;
  }

  public static String toIpV6String(final BigInteger intIpV6) {
    StringBuilder str = new StringBuilder();
    BigInteger ipV6 = BigInteger.valueOf(intIpV6.longValue());
    BigInteger ff = BigInteger.valueOf(0xffff);
    for (int i = 0; i < 8; i++) {
      str.insert(0, ipV6.and(ff).toString(16) + ":");
      ipV6 = ipV6.shiftRight(16);
    }
    //去掉最后的：号
    str.setLength(str.length() - 1);
    return str.toString().replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
  }

  public static String toCompleteIpV6(final String strIpv6) {
    final BigInteger big = toIpV6Int(strIpv6);
    final StringBuilder str = new StringBuilder(big.toString(16));
    final StringBuilder completeIpv6Str = new StringBuilder(40);
    while (str.length() != 32) {
      str.insert(0, 0);
    }
    for (int i = 0; i <= str.length(); i += 4) {
      completeIpv6Str.append(str.substring(i, i + 4));
      if ((i + 4) == str.length()) {
        break;
      }
      completeIpv6Str.append(":");
    }
    return completeIpv6Str.toString();
  }

  public static boolean isIpV6Loopback(final String ip) {
    return isIpV6(ip) && (ip.equals("::1") || ip.equals("0:0:0:0:0:0:0:1"));
  }

  /**
   * Gets the remote IP address from the HttpHeaders.
   * @param headers Http Headers
   * @param defaultIpSupplier Default IP supplier
   * @return  Remote IP address
   */
  public static String getClientIpAddress(final Map<String, List<String>> headers, final Supplier<String> defaultIpSupplier) {
    Objects.requireNonNull(headers);
    Objects.requireNonNull(defaultIpSupplier);
    String ip = CollectionUtils.firstElement(headers.get("X-Forwarded-For"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("X-Forwarded-For"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("Proxy-Client-IP"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("WL-Proxy-Client-IP"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("HTTP_CLIENT_IP"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("HTTP_X_FORWARDED_FOR"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = CollectionUtils.firstElement(headers.get("X-Real-IP"));
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = defaultIpSupplier.get();
    if (isIpV6Loopback(ip)) {
      return "127.0.0.1";
    }
    return ip;
  }

  private static boolean isValidIpAddress(final String ip) {
    return (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip));
  }
}
