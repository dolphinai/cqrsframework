package com.github.larkvii.cqrsframework.commons.util;

import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.Optional;

/**
 */
public final class WebUtils {

  private WebUtils() {}

  public static Optional<String> getClientIpAddress(final HttpHeaders headers) {
    Objects.requireNonNull(headers);
    String ip = headers.getFirst("X-Forwarded-For");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("X-Forwarded-For");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("Proxy-Client-IP");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("WL-Proxy-Client-IP");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("HTTP_CLIENT_IP");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    ip = headers.getFirst("X-Real-IP");
    if(isValidAddress(ip)) {
      return Optional.of(ip);
    }
    return Optional.empty();
  }

  private static boolean isValidAddress(String ip) {
    return (!StringUtils.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip));
  }
}
