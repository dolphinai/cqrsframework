package com.github.dolphinai.cqrsframework.commons.util;

import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
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
    if (octets != null && octets.length == 4) {
      return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
        + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }
    return null;
  }

  public static BigInteger toIpV6Int(final String ipv6){
    int compressIndex = ipv6.indexOf("::");
    if (compressIndex != -1){
      String part1s = ipv6.substring(0, compressIndex);
      String part2s = ipv6.substring(compressIndex + 1);
      BigInteger part1 = toIpV6Int(part1s);
      BigInteger part2 = toIpV6Int(part2s);
      int part1hasDot = 0;
      char ch[] = part1s.toCharArray();
      for(char c : ch){
        if(c == ':'){
          part1hasDot++;
        }
      }
      return part1.shiftLeft(16 * (7 - part1hasDot)).add(part2);
    }
    String[] str = ipv6.split(":");
    BigInteger big = BigInteger.ZERO;
    for(int i = 0; i < str.length; i++){
      //::1
      if(str[i].isEmpty()){
        str[i] = "0";
      }
      big = big.add(BigInteger.valueOf(Long.valueOf(str[i], 16)).shiftLeft(16 * (str.length - i - 1)));
    }
    return big;
  }

  public static String toIpV6String(final BigInteger intIpV6){
    String str = "";
    BigInteger ipV6 = BigInteger.valueOf(intIpV6.longValue());
    BigInteger ff = BigInteger.valueOf(0xffff);
    for (int i = 0; i < 8; i++){
      str = ipV6.and(ff).toString(16) + ":" + str;
      ipV6 = ipV6.shiftRight(16);
    }
    //去掉最后的：号
    str = str.substring(0, str.length() - 1);
    return str.replaceFirst("(^|:)(0+(:|$)){2,8}", "::");
  }

  public static String toCompleteIpV6(final String strIpv6){
    final BigInteger big = toIpV6Int(strIpv6);
    String str = big.toString(16);
    String completeIpv6Str = "";
    while(str.length() != 32){
      str = "0" + str;
    }
    for (int i = 0; i <= str.length(); i += 4){
      completeIpv6Str += str.substring(i, i + 4);
      if ((i + 4) == str.length()){
        break;
      }
      completeIpv6Str += ":";
    }
    return completeIpv6Str;
  }

  /**
   * Gets the remote IP address from the HttpHeaders.
   * @param headers Http Headers
   * @param defaultIpSupplier Default IP supplier
   * @return  Remote IP address
   */
  public static String getClientIpAddress(final MultiValueMap<String, String> headers, final Supplier<String> defaultIpSupplier) {
    Objects.requireNonNull(headers);
    Objects.requireNonNull(defaultIpSupplier);
    String ip = headers.getFirst("X-Forwarded-For");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("X-Forwarded-For");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("Proxy-Client-IP");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("WL-Proxy-Client-IP");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("HTTP_CLIENT_IP");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = headers.getFirst("X-Real-IP");
    if (isValidIpAddress(ip)) {
      return ip;
    }
    ip = defaultIpSupplier.get();
    if (ip != null && (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("::1"))) {
      return "127.0.0.1";
    }
    return ip;
  }

  private static boolean isValidIpAddress(String ip) {
    return (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip));
  }

}
