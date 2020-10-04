package com.github.larkvii.cqrsframework.vertx;

import org.jboss.resteasy.spi.ResourceFactory;

import java.util.*;

/**
 *
 */
public final class RestServerContext {

  private final Integer portValue;
  private final Boolean swaggerEnableValue;
  private final List<ResourceFactory> resourceFactoriesValue = new Vector<>();
  private final List<Object> providersValue = new Vector<>();

  public RestServerContext(final Integer port, final Boolean swaggerEnable) {
    this.portValue = port;
    this.swaggerEnableValue = swaggerEnable;
  }

  public Integer port() {
    return portValue;
  }

  public boolean swaggerEnable() {
    return Boolean.TRUE.equals(swaggerEnableValue);
  }

  public List<ResourceFactory> resourceFactories() {
    return resourceFactoriesValue;
  }

  public List<Object> providers() {
    return providersValue;
  }
}
