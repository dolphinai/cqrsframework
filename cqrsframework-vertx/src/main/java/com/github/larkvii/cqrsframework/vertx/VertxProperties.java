package com.github.larkvii.cqrsframework.vertx;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "vertx")
@Data
public class VertxProperties {

  private Integer eventLoopMaxExecuteTime;
  private Integer eventLoopPoolSize;
  private Integer workerPoolSize;
  private Integer metricsPort;
  private String metricsEndpoint;
  private Boolean swaggerEnable;

}
