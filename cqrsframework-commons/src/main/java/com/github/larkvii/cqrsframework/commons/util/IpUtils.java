package com.github.larkvii.cqrsframework.commons.util;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 *
 */
public final class IpUtils {

  private IpUtils() {
  }

  public static boolean isIpV6(final String ip) {
    return ip != null && ip.indexOf(':') > -1;
  }

  public static String toIpV4(final long ipV4Long) {
    int octet3 = (int) ((ipV4Long >> 24) % 256);
    int octet2 = (int) ((ipV4Long >> 16) % 256);
    int octet1 = (int) ((ipV4Long >> 8) % 256);
    int octet0 = (int) ((ipV4Long) % 256);
    return octet3 + "." + octet2 + "." + octet1 + "." + octet0;
  }

  public static Long toIpV4Long(final String ipV4) {
    // IPv4: 32 bits
    final String[] octets = StringUtils.tokenizeToStringArray(ipV4, "\\.");
    if (octets != null && octets.length == 4) {
      return (Long.parseLong(octets[0]) << 24) + (Integer.parseInt(octets[1]) << 16)
        + (Integer.parseInt(octets[2]) << 8) + Integer.parseInt(octets[3]);
    }
    return null;
  }

  public static Optional<String> getClientIpAddress(final HttpHeaders headers) {
    Objects.requireNonNull(headers);
    String ip = headers.getFirst("X-Forwarded-For");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("X-Forwarded-For");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("Proxy-Client-IP");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("WL-Proxy-Client-IP");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("HTTP_CLIENT_IP");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("X-Real-IP");
    if(isValidIpAddress(ip)) {
      return Optional.of(ip);
    }
    return Optional.empty();
  }

  private static boolean isValidIpAddress(String ip) {
    return (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip));
  }

}
