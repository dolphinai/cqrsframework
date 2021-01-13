package com.github.dolphinai.cqrsframework.open;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import lombok.Value;
import lombok.With;

import java.beans.Transient;
import java.io.Serializable;

/**
 */
@Value
public final class ApiToken implements Serializable {

  @With
  private String accessToken;
  private String refreshToken;
  private Integer expiresIn;

  @Transient
  public boolean isValid() {
    return StringHelper.isNotEmpty(accessToken);
  }

  public static ApiToken of(final String accessToken, final String refreshToken, final Integer expiresIn) {
    return new ApiToken(accessToken, refreshToken, expiresIn);
  }
}
