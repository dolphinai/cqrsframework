package com.github.larkvii.cqrsframework.commons.util;

import org.springframework.util.StringUtils;

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
}
