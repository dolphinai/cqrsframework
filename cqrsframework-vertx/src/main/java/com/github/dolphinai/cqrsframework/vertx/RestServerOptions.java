package com.github.dolphinai.cqrsframework.vertx;

import org.jboss.resteasy.spi.ResourceFactory;

import java.util.List;

/**
 */
public interface RestServerOptions {

  int getPort();

  void setPort(int port);

  List<ResourceFactory> resourceFactories();

  List<Object> providers();

  default boolean swaggerEnable() {
    return true;
  }
}
