package com.github.dolphinai.cqrsframework.open;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;

/**
 */
@Data
public final class ApiToken implements Serializable {

  private String accessToken;
  private String refreshToken;
  private Integer expiresIn;

  @Transient
  public boolean isValid() {
    return StringHelper.isNotEmpty(accessToken);
  }

  public static ApiToken of(final String accessToken, final String refreshToken, final Integer expiresIn) {
    ApiToken result = new ApiToken();
    result.setAccessToken(accessToken);
    result.setRefreshToken(refreshToken);
    result.setExpiresIn(expiresIn);
    return result;
  }
}
