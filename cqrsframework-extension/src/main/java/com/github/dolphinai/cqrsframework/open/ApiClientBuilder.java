package com.github.dolphinai.cqrsframework.open;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.hystrix.HystrixFeign;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 */
public class ApiClientBuilder {

  private final Feign.Builder builder;

  public ApiClientBuilder(final Feign.Builder builder) {
    this.builder = builder;
  }

  public Feign.Builder getBuilder() {
    return builder;
  }

  public ApiClientBuilder apply(final Consumer<Feign.Builder> builderInitializer) {
    builderInitializer.accept(this.builder);
    return this;
  }

  public ApiClientBuilder withLogger(final Logger logger, final Logger.Level logLevel) {
    this.builder.logger(logger).logLevel(logLevel);
    return this;
  }

  public ApiClientBuilder withInterceptor(final RequestInterceptor interceptor) {
    this.builder.requestInterceptor(interceptor);
    return this;
  }

  public ApiClientBuilder withHeader(final String headerName, final Supplier<String> valueHandler) {
    return withInterceptor(new RequestInterceptor() {
      @Override
      public void apply(final RequestTemplate template) {
        // Authorization: Bearer
        template.header(headerName, valueHandler.get());
      }
    });
  }

  public ApiClientBuilder withAuthorization(final Supplier<String> valueHandler) {
    return withHeader("Authorization", valueHandler);
  }

  public <T> T create(final Class<T> apiType, final String url) {
    return getBuilder().target(apiType, url);
  }

  public <T> T create(final Class<T> apiType, final String url, T apiFallbackInstance) {
    if(this.builder instanceof  HystrixFeign.Builder) {
      return ((HystrixFeign.Builder)this.builder).target(apiType, url, apiFallbackInstance);
    }
    return create(apiType, url);
  }

  public static ApiClientBuilder instance() {
    return ApiClientBuilder.ApiClientBuilderHolder.instance;
  }

  private static class ApiClientBuilderHolder {
    private static final ApiClientBuilder instance = new ApiClientBuilder(new Feign.Builder());
  }
}
