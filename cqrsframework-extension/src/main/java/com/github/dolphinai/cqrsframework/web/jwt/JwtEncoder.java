package com.github.dolphinai.cqrsframework.web.jwt;

import com.github.dolphinai.cqrsframework.common.util.Moment;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 */
public interface JwtEncoder {

  default String encode(String subject, int expirySeconds) {
    Objects.requireNonNull(subject);
    final Moment moment = Moment.now();
    return encode(subject, moment.toDate(), moment.addSeconds(expirySeconds).toDate());
  }

  String encode(String subject, Date issueDate, Date expiryDate);

  default String encodeMap(Map<String, Object> claims, int expirySeconds) {
    final Moment moment = Moment.now();
    return encodeMap(claims, moment.toDate(), moment.addSeconds(expirySeconds).toDate());
  }

  String encodeMap(Map<String, Object> claims, Date issueDate, Date expiryDate);

  String decode(String jwt);

  Map<String, Object> decodeMap(String jwt);

  Date getExpiresAt(String jwt);
}
