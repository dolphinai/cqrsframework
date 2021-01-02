package com.github.dolphinai.cqrsframework.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.function.Function;

/**
 */
public class ApiClientContextImpl implements ApiClientContext {

  private static final Logger log = LoggerFactory.getLogger(ApiClientContext.class);
  private final Function<ApiClientToken, ApiClientToken> tokenRefreshHandler;
  private ApiClientToken token;
  private LocalDateTime expiration;

  public ApiClientContextImpl(final Function<ApiClientToken, ApiClientToken> refreshHandler) {
    this.tokenRefreshHandler = refreshHandler;
  }

  @Override
  public String getToken() {
    if (isExpired()) {
      synchronized (this) {
        if (isExpired()) {
          tryGetToken();
        }
      }
    }
    return token.getAccessToken();
  }

  @Override
  public LocalDateTime getExpiration() {
    return expiration;
  }

  @Override
  public boolean isExpired() {
    return token == null || (this.expiration != null && LocalDateTime.now().isAfter(this.expiration));
  }

  @Override
  public String tryGetToken() throws AccessTokenException {
    try {
      token = tokenRefreshHandler.apply(token);
    } catch (Exception e) {
      throw new AccessTokenException(e);
    }
    if (token == null) {
      throw new AccessTokenException("Missing the access token");
    }
    expiration = LocalDateTime.now().plusSeconds(token.getExpiresIn() - 5);
    return token.getAccessToken();
  }
}
