package com.github.larkvii.cqrsframework.vertx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */
@Data
@ConfigurationProperties(prefix = "vertx")
public class VertxProperties {

  private Integer eventLoopMaxExecuteTime;
  private Integer eventLoopPoolSize;
  private Integer workerPoolSize;
  private Boolean swaggerEnable;

}
