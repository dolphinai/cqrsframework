package com.github.larkvii.cqrsframework.vertx;

import com.github.larkvii.cqrsframework.swagger.SwaggerOpenApiContextBuilder;
import com.github.larkvii.cqrsframework.swagger.SwaggerOpenApiDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.ws.rs.core.Application;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Configuration
public class VertxConfiguration {

  @Value("${server.port:8080}")
  private Integer serverPort;
  @Value("${vertx.swagger-enable:false}")
  private Boolean swaggerEnable;

  @Bean
  public RestServerContext restServerContext() {
    return new RestServerContext(serverPort, swaggerEnable);
  }

  @Bean
  public RestResourceAndProviderPostProcessor restResourceAndProviderPostProcessor(final RestServerContext restServerContext) {
    return new RestResourceAndProviderPostProcessor(restServerContext);
  }

  @Bean
  @ConditionalOnProperty("vertx.swagger-enable")
  @ConditionalOnBean(OpenApiContext.class)
  public SwaggerOpenApiDoc swaggerOpenApiDoc() {
    return new SwaggerOpenApiDoc();
  }

  @Bean
  @ConditionalOnBean(Application.class)
  public OpenApiContext openApiContext(final ApplicationContext applicationContext, @Nullable final Application application) {
    final Collection<Object> resourceBeans = applicationContext.getBeansWithAnnotation(Tag.class).values();

    OpenApiContext apiContext = null;
    if (!resourceBeans.isEmpty()) {
      SwaggerConfiguration oasConfig = new SwaggerConfiguration()
        .resourceClasses(resourceBeans.stream().map(c -> c.getClass().getName()).collect(Collectors.toSet()));

      // init context
      try {
        apiContext = new SwaggerOpenApiContextBuilder<>(application)
          .openApiConfiguration(oasConfig)
          .buildContext(true);
      } catch (OpenApiConfigurationException e) {
        log.error("Failed to initialize OpenApiContext", e);
      }
    }

    return apiContext;
  }
}
