package com.github.dolphinai.cqrsframework.open;

import com.github.dolphinai.cqrsframework.common.util.StringHelper;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;

/**
 */
@Data
public final class ApiClientToken implements Serializable {

  private String accessToken;
  private String refreshToken;
  private Integer expiresIn;

  @Transient
  public boolean isValid() {
    return StringHelper.isNotEmpty(accessToken);
  }
}
