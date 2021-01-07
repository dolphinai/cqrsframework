package com.github.dolphinai.cqrsframework.open;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.hystrix.HystrixFeign;

import java.util.function.Supplier;

/**
 */
public interface ApiClientBuilder {

  Feign.Builder getBuilder();

  default ApiClientBuilder withLevel(Logger.Level logLevel) {
    getBuilder().logLevel(logLevel);
    return this;
  }

  default ApiClientBuilder withInterceptor(RequestInterceptor interceptor) {
    getBuilder().requestInterceptor(interceptor);
    return this;
  }

  default ApiClientBuilder withAuthorization(Supplier<String> valueHandler) {
    return withHeader("Authorization", valueHandler);
  }

  default ApiClientBuilder withHeader(String headerName, Supplier<String> valueHandler) {
    return withInterceptor(new RequestInterceptor() {
      @Override
      public void apply(final RequestTemplate template) {
        // Authorization: Bearer
        template.header(headerName, valueHandler.get());
      }
    });
  }

  default <T> T create(final Class<T> apiType, final String url) {
    return getBuilder().target(apiType, url);
  }

  default <T> T create(final Class<T> apiType, final String url, T apiFallbackInstance) {
    return ((HystrixFeign.Builder)getBuilder()).target(apiType, url, apiFallbackInstance);
  }

}
