package com.github.dolphinai.cqrsframework.open;

import java.time.LocalDateTime;

/**
 */
public interface ApiClientContext {

  String getToken();

  LocalDateTime getExpiration();

  boolean isExpired();

  String tryGetToken() throws AccessTokenException;
}
