package com.github.larkvii.cqrsframework.swagger;

import com.github.larkvii.cqrsframework.vertx.AbstractApplication;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import io.swagger.v3.oas.integration.api.OpenApiContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 */
@Slf4j
public final class SwaggerUtils {

  private SwaggerUtils(){}

  public static OpenApiContext createOpenApiContext(final ApplicationContext applicationContext, final AbstractApplication application) {
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

  public static SwaggerOpenApiDocument createOpenApiDocument(final OpenApiContext context) {
    return new SwaggerOpenApiDocument(context);
  }
}
